package all.doo.service.impl;


import all.doo.dao.LeaseMapper;
import all.doo.dao.TenantMapper;
import all.doo.entity.Lease;
import all.doo.entity.empinfo;
import all.doo.entity.pojo.LeasePojo;
import all.doo.entity.pojo.LeaseinfoPojo;
import all.doo.entity.pojo.TenantPojo;
import all.doo.service.LeaseService;
import all.doo.utils.DooUtils;
import all.doo.utils.FkExcelUtils;
import cn.doo.email.EmailService;
import cn.doo.framework.entity.pojo.Leaseinfo;
import cn.doo.framework.entity.pojo.RepertoryPojo;
import cn.doo.repertory.service.RepertoryService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rj.bd.service.IFeignPersonnelService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
//@Transactional(rollbackFor = Exception.class) 不能统一事务管理 否则会冲突
public class LeaseServiceimpl implements LeaseService {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;
    private final Lock lock = new ReentrantLock();

    @Autowired
    private LeaseMapper leaseMapper;

    @Autowired
    RepertoryService repertoryService;

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private IFeignPersonnelService feignPersonnelService;

    @Autowired
    private EmailService emailService;


    /**
     * 分页查询
     *
     * @param phone
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryAll(String phone, Integer page, Integer limit) throws Exception {

        PageHelper.startPage(page, limit);
        Page<Lease> pagez = leaseMapper.queryAll();
        return DooUtils.print(0, "请求成功", pagez.getResult(), pagez.getTotal());
//        return DooUtils.print(0, "请求成功", pagez, null);
    }

    /**
     * 新增一个订单信息
     *
     * @param leaseinfo
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> insertOne(List<LeaseinfoPojo> leaseinfo, Integer uid) throws Exception {
        //获取订单信息
        if (leaseinfo.size() > 7) {
            return DooUtils.print(-2, "租赁样式过多,请分批次租赁", null, null);
        }

        TenantPojo tenantPojo = tenantMapper.selectById(uid);
        if (tenantPojo == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        //初始化
        LeasePojo leasePojo;
        //总押金
        Integer sumDeposit = 0;
        //Leaseinfo对象
        List<Leaseinfo> leaseinfos = new ArrayList<>();
        /**
         * 计算总押金 并 获取Leaseinfos整体对象
         */
        //事务对象
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        //开启锁
        lock.lock();
        try {
            for (LeaseinfoPojo leaseinfoPojo : leaseinfo) {

                //总押金
                sumDeposit += getDeposit(leaseinfoPojo);

                //获取单个Leaseinfo对象
                Leaseinfo tempLeaseinfo = getLeaseinfo(leaseinfoPojo);
                //添加Leaseinfos对象
                leaseinfos.add(tempLeaseinfo);

                //校验库存数量 并 减少库存数量
                reduceCount(leaseinfoPojo);
            }

            //计算结束 获取总押金 和 leaseinfo Json串
            ObjectMapper objectMapper = new ObjectMapper();
            String leaseinfoJson = objectMapper.writeValueAsString(leaseinfos);

            //创建LeasePojo对象 开始添加数据
            leasePojo = new LeasePojo();
            leasePojo.setLeaseinfo(leaseinfoJson);
            leasePojo.setUid(uid);
            leasePojo.setRent(0);
            leasePojo.setBreakinfo("null");
            leasePojo.setCreatetime(new Date());
            leasePojo.setDeposit(sumDeposit);
            leasePojo.setStatus(0);

            leaseMapper.insert(leasePojo);

            System.out.println("没有异常，准备提交事务");
            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            System.out.println("有异常，准备回滚所有事务");
            e.printStackTrace();
            platformTransactionManager.rollback(transaction);
            return DooUtils.print(-2, "参数异常", null, null);
        } finally {
            lock.unlock();
        }

        return DooUtils.print(0, "添加成功", sumDeposit, null);
    }

    /**
     * 结束租赁
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> updateOne(List<Leaseinfo> leaseinfos, Integer id) throws Exception {
        //获取订单信息
        LeasePojo leasePojo = leaseMapper.selectById(id);
        if (leasePojo == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }


        //获取租赁信息
        String leaseinfo = leasePojo.getLeaseinfo();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Leaseinfo> list = objectMapper.readValue(leaseinfo, new TypeReference<List<Leaseinfo>>() {
        });

        System.out.println("list = " + list);
        System.out.println("list = " + list);
        System.out.println("list = " + list);


        //存放租金
        AtomicInteger result = new AtomicInteger();

        //事务对象
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        //开启锁
        lock.lock();
        try {
            for (Leaseinfo item : list) {
                //租赁的商品id
                Integer lid = item.getId();
                //租赁的个数
                int size = item.getNumber().size();

                //获取对应商品对象
                //调用fegin接口
//                RepertoryPojo repertoryPojo = (RepertoryPojo) repertoryService.getOne(lid).get("data");

                Object data = repertoryService.getOne(lid).get("data");
                System.out.println("data = " + data);

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(data);
                RepertoryPojo repertoryPojo = mapper.readValue(json, RepertoryPojo.class);

                //获取单价
                Integer unitprice = repertoryPojo.getUnitprice();

                //计算游玩几小时
                long startTime = leasePojo.getCreatetime().getTime();
                long endTime = System.currentTimeMillis() - startTime;
                int playTimeCount = countTime(endTime, 0);

                //获取总价
                int sum = size * unitprice * playTimeCount;
                result.addAndGet(sum);

                leasePojo.setRent(result.get());
                leasePojo.setEndtime(new Date());
                leasePojo.setStatus(1);

                //是否有损坏
                AtomicBoolean flag = new AtomicBoolean(false);
                leaseinfos.forEach(obj -> {
                    if (obj.getNumber().size() != 0) {
                        flag.set(true);
                    }
                });


                //有损坏
                if (flag.get()) {
                    try {
                        //设置损坏信息
                        leasePojo.setBreakinfo(objectMapper.writeValueAsString(leaseinfos));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    leaseinfos.forEach(temp -> {
                        //如果是这个类型 则取出损坏数量
                        if (temp.getId().equals(lid)) {
                            //设置归还数量
                            repertoryPojo.setCount(repertoryPojo.getCount() + size - temp.getNumber().size());
                        }
                    });


                    Map<String, Object> add = feignPersonnelService.add(leaseinfos);
                    Object dataJson = add.get("data");
                    if (dataJson != null) {
                        Map<String, Object> jsonString = (Map<String, Object>) dataJson;
                        emailService.sendEmail(jsonString.get("email") + "", "null", 1);
                    }


                } else {
                    leasePojo.setBreakinfo("null");
                    //设置归还数量
                    repertoryPojo.setCount(repertoryPojo.getCount() + size);
                }

                leaseMapper.updateById(leasePojo);
                //调用fegin接口
//                repertoryMapper.updateById(repertoryPojo);
                repertoryService.updateOne(repertoryPojo);
            }
            System.out.println("没有异常，准备提交事务");
            platformTransactionManager.commit(transaction);
        } catch (Exception e) {
            System.out.println("有异常，准备回滚所有事务");
            e.printStackTrace();
            platformTransactionManager.rollback(transaction);
            return DooUtils.print(-5, "服务器内部错误", null, null);
        } finally {
            lock.unlock();
        }

        return DooUtils.print(0, "结束成功", null, null);
    }

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> deleteOne(Integer id) {
        leaseMapper.deleteById(id);
        return DooUtils.print(0, "删除成功", null, null);
    }

    @Override
    public Map<String, Object> download(Integer id, HttpServletResponse response) throws IOException {

        //存储
        Map<String, Object> hashMap = new HashMap<>();

        //获取订单对象
        Lease lease = leaseMapper.queryOne(id);
        if (lease == null) {
            return DooUtils.print(-2, "暂无此订单", null, null);
        }

        //获取租赁信息
        String leaseinfo = lease.getLeaseinfo();
        ObjectMapper objectMapper = new ObjectMapper();

        //获取租赁订单对象
        List<Leaseinfo> leaseinfos = objectMapper.readValue(leaseinfo, new TypeReference<List<Leaseinfo>>() {
        });

        //获取对象
        List<empinfo> arr = new ArrayList();

        AtomicInteger sum = new AtomicInteger();
        //获取对应商品的价格
        for (Leaseinfo item : leaseinfos) {
            //商品id
            Integer tempId = item.getId();

            Object data = repertoryService.getOne(item.getId()).get("data");
            System.out.println("data = " + data);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);
            RepertoryPojo repertoryPojo = mapper.readValue(json, RepertoryPojo.class);

            //获取商品名字
            String name = repertoryPojo.getName();
            //获取对应商品id的每件租金
            Integer unitdeposit = repertoryPojo.getUnitdeposit();
            //商品数量
            int size = item.getNumber().size();

            sum.addAndGet(unitdeposit * size);

            arr.add(new empinfo(name, unitdeposit, size));
        }

        //租户信息
        TenantPojo user = lease.getUser();


        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        String item = sformat.format(lease.getCreatetime());

        //存放FK所需数据
        hashMap.put("empinfo", arr);
        hashMap.put("user", user);
        hashMap.put("time", item);
        hashMap.put("money", sum.get());

        System.out.println(hashMap);

        //设置fk路径目录
        String path = System.getProperty("user.dir");
        System.out.println(path);
        String ftl = path + "\\TourismLease-LeaseService-8002\\src\\main\\resources\\templates";
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(ftl));

//        //使用封装的工具类 并打印执行结果
        String test = FkExcelUtils.exportExcel(response, configuration, "empinfo.ftl", hashMap, item + "_" + user.getName() + ".xls");
        System.out.println("test : " + test);


        return DooUtils.print(0, "打印成功", null, null);
    }


    /**
     * 判断商品id是几位数 并返回前缀 eg: 001 010 100
     *
     * @param id
     * @return
     */
    private String getUuidPre(Integer id) {
        //生成uuid策略
        String ids = id + "";
        //获取几位数
        int length = ids.length();
        if (length == 1) {
            //个位数
            ids = "00" + ids;
        } else if (length == 2) {
            //十位数
            ids = "0" + ids;
        }
        return ids;
    }

    /**
     * 获取总押金
     *
     * @param leaseinfoPojo
     * @return
     */
    private Integer getDeposit(LeaseinfoPojo leaseinfoPojo) throws IOException {
        //拿到id并在数据库查询商品租赁押金,拿到租赁押金
        Integer id = leaseinfoPojo.getId();

        //这里需要调用fegin去查寻
//        RepertoryPojo repertoryPojo = (RepertoryPojo) repertoryService.getOne(id).get("data");
        Object data = repertoryService.getOne(id).get("data");
        System.out.println("data = " + data);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        RepertoryPojo repertoryPojo = mapper.readValue(json, RepertoryPojo.class);

//      repertoryPojo = repertoryMapper.selectById(id);

        Integer unitdeposit = repertoryPojo.getUnitdeposit();
        //拿到租赁的商品数量,并与租赁数量相乘得出总押金
        Integer count = leaseinfoPojo.getCount();
        return unitdeposit * count;
    }


    private Leaseinfo getLeaseinfo(LeaseinfoPojo leaseinfoPojo) throws JsonProcessingException {

        //商品id
        Integer repertoryId = leaseinfoPojo.getId();


        //单个对象信息
        Leaseinfo leaseinfo = new Leaseinfo();
        //设置商品id
        leaseinfo.setId(repertoryId);

        //生成对应uuid
        //获取此商品需要生成多少个uuid
        Integer count = leaseinfoPojo.getCount();
        //uuids容器 存放需要生成的uuid
        List<String> uuids = new ArrayList<>();

        //生成商品uuid
        for (int i = 0; i < count; i++) {
            //生成uuid 长度为6
            String temp = IdUtil.simpleUUID();
            String sub = StrUtil.sub(temp, 0, 6);
            //获取uuid前缀
            String uuidPre = getUuidPre(repertoryId);
            //获取uuid
            String uuid = sub + uuidPre;
            //添加uuid 到 uuids数组
            uuids.add(uuid);
        }
        //存放uuids对象
        leaseinfo.setNumber(uuids);

        return leaseinfo;
    }

    /**
     * 减少库存
     *
     * @param leaseinfoPojo
     * @throws Exception
     */
    private void reduceCount(LeaseinfoPojo leaseinfoPojo) throws Exception {
        //这里需要调用feign接口
//        RepertoryPojo repertoryPojo = null;
//        RepertoryPojo repertoryPojo = repertoryMapper.selectById(leaseinfoPojo.getId());

        Object data = repertoryService.getOne(leaseinfoPojo.getId()).get("data");
        System.out.println("data = " + data);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        RepertoryPojo repertoryPojo = mapper.readValue(json, RepertoryPojo.class);


        if (repertoryPojo == null) {
            throw new Exception("此产品在库存中不存在");
        } else {
            //要租的数量
            Integer leaseCount = leaseinfoPojo.getCount();
            //现在库存的数量
            Integer repertoryCount = repertoryPojo.getCount();
            if (repertoryCount < leaseCount) {
                throw new Exception("库存数量不够");
            }
//            leaseMapper.reduceCount(leaseCount, leaseinfoPojo.getId());
            repertoryService.countOperation(leaseinfoPojo.getId(), leaseCount, 0);
        }
    }

    /**
     * 计算游玩几小时
     *
     * @param timing
     * @return
     */
    private static int countTime(long timing, int count) {
        if (timing < 1000 * 60 * 20) {
            return count;
        } else if (timing < 1000 * 60 * 60) {
            return ++count;
        } else {
            timing -= 1000 * 60 * 60;
            return countTime(timing, ++count);
        }
    }
}

package cn.doo.repertory.service.impl;

import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.dao.GoodstypeMapper;
import cn.doo.repertory.dao.RepertoryMapper;
import cn.doo.repertory.entity.pojo.GoodstypePojo;
import cn.doo.repertory.entity.pojo.RepertoryPojo;
import cn.doo.repertory.service.RepertoryService;
import cn.doo.repertory.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class RepertoryServiceimpl implements RepertoryService {

    @Autowired
    private GoodstypeMapper goodstypeMapper;
    @Autowired
    private RepertoryMapper repertoryMapper;
    @Autowired
    private RedisUtil jedis;

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        Integer currentPage = DooUtils.getCurrentPage(page, limit);
        return DooUtils.print(0, "请求成功", repertoryMapper.queryAll(currentPage, limit), repertoryMapper.selectCount(null));
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @Override
    public Map<String, Object> insertOne(RepertoryPojo repertoryPojo) {
        //从数据库查询type值是否存在于商品种类表  不存在则返回信息
        GoodstypePojo goodstypePojo = goodstypeMapper.selectById(repertoryPojo.getType());
        if (goodstypePojo == null) {
            return DooUtils.print(-3, "该商品种类不存在于数据库", null, null);
        }

        repertoryMapper.insert(repertoryPojo);

        return DooUtils.print(0, "新增成功", null, null);
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @Override
    public Map<String, Object> updateOne(RepertoryPojo repertoryPojo) {
        //从数据库查询type值是否存在于商品种类表  不存在则返回信息
        GoodstypePojo goodstypePojo = goodstypeMapper.selectById(repertoryPojo.getType());
        if (goodstypePojo == null) {
            return DooUtils.print(-3, "该商品种类不存在于数据库", null, null);
        }
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(repertoryPojo.getId());
        if (repertoryPojo1 == null) {
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }

        repertoryMapper.updateById(repertoryPojo);
        return DooUtils.print(0, "修改成功", null, null);
    }

    /**
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    @Override
    public Map<String, Object> deleteOne(Integer id) {
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(id);
        if (repertoryPojo1 == null) {
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }

        repertoryMapper.deleteById(id);
        return DooUtils.print(0, "删除成功", null, null);
    }

    /**
     * @param id
     * @return
     * @desc 根据id获取库存信息
     */
    @Override
    public Map<String, Object> getOne(Integer id) {
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(id);
        if (repertoryPojo1 == null) {
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }
        return DooUtils.print(0, "请求成功", repertoryPojo1, null);
    }

    /**
     * @param id
     * @param count
     * @param type
     * @return
     * @desc 根据id操作数量
     */
    @Override
    public Map<String, Object> countOperation(Integer id, Integer count, Integer type) {
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(id);
        if (repertoryPojo1 == null) {
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }
        if (type == 1) {
            //添加商品
            Integer count1 = repertoryPojo1.getCount();
            count1 += count;
            repertoryPojo1.setCount(count1);
            this.repertoryMapper.updateById(repertoryPojo1);
        } else if (type == 0) {
            //减少商品
            Integer count1 = repertoryPojo1.getCount();
            count1 -= count;
            repertoryPojo1.setCount(count1);
            this.repertoryMapper.updateById(repertoryPojo1);
        } else {
            return DooUtils.print(-3, "数据操作类型错误", null, null);
        }
        return DooUtils.print(0, "操作成功", null, null);
    }

}

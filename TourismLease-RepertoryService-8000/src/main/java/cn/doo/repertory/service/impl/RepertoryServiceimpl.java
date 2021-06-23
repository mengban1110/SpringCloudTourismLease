package cn.doo.repertory.service.impl;

import cn.doo.framework.entity.pojo.GoodstypePojo;
import cn.doo.framework.entity.pojo.RepertoryPojo;
import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.dao.GoodstypeMapper;
import cn.doo.repertory.dao.RepertoryMapper;
import cn.doo.repertory.service.RepertoryService;
import cn.doo.repertory.utils.redis.RedisUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
    @HystrixCommand(fallbackMethod = "processHystrixCircuitBreakMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败60%也就是6次的时候就跳闸（触发服务降级）
    })
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        return DooUtils.print(0, "请求成功", repertoryMapper.queryAll(page, limit), repertoryMapper.selectCount(null));
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @Override
    @HystrixCommand(fallbackMethod = "processHystrixCircuitBreakMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败60%也就是6次的时候就跳闸（触发服务降级）
    })
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
    @HystrixCommand(fallbackMethod = "processHystrixCircuitBreakMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败60%也就是6次的时候就跳闸（触发服务降级）
    })
    public Map<String, Object> updateOne(RepertoryPojo repertoryPojo) {
        //从数据库查询type值是否存在于商品种类表  不存在则返回信息
        GoodstypePojo goodstypePojo = goodstypeMapper.selectById(repertoryPojo.getType());
        if (goodstypePojo == null) {
            return DooUtils.print(-3, "该商品种类不存在于数据库", null, null);
        }
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(repertoryPojo.getId());
        if (repertoryPojo1==null){
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
    @HystrixCommand(fallbackMethod = "processHystrixCircuitBreakMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"), //失败60%也就是6次的时候就跳闸（触发服务降级）
    })
    public Map<String, Object> deleteOne(Integer id) {
        //从数据库查询id值是否存在于商品表  不存在则返回信息
        RepertoryPojo repertoryPojo1 = repertoryMapper.selectById(id);
        if (repertoryPojo1==null){
            return DooUtils.print(-3, "该商品不存在于数据库", null, null);
        }

        repertoryMapper.deleteById(id);
        return DooUtils.print(0, "删除成功", null, null);
    }


}

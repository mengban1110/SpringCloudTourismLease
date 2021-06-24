package cn.doo.repertory.service.fallbacks;

import cn.doo.repertory.entity.pojo.RepertoryPojo;
import cn.doo.repertory.service.RepertoryService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-24-8:45
 */
@Component("RepertoryServiceFallback")
public class RepertoryServiceFallback implements RepertoryService {
    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @Override
    public Map<String, Object> queryAll(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        return null;
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @Override
    public Map<String, Object> insertOne(@RequestBody RepertoryPojo repertoryPojo) {
        return null;
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @Override
    public Map<String, Object> updateOne(@RequestBody RepertoryPojo repertoryPojo) {
        return null;
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 删除一个仓库商品
     */
    @Override
    public Map<String, Object> deleteOne(@RequestBody RepertoryPojo repertoryPojo) {
        return null;
    }

    /**
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    @Override
    public Map<String, Object> getOne(@RequestParam("id") Integer id) {
        return null;
    }

    /**
     * 根据id 对仓库数量进行操作 type=1添加 0减少       校验自行在自己的方法里写
     *
     * @param id
     * @param count
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> countOperation(@RequestParam("id") Integer id, @RequestParam("count") Integer count, @RequestParam("type") Integer type) {
        return null;
    }


}

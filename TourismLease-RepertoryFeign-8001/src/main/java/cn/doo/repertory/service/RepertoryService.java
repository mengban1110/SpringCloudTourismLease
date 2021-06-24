package cn.doo.repertory.service;


import cn.doo.framework.entity.pojo.RepertoryPojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "TourismLease-RepertoryService")
public interface RepertoryService {

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @RequestMapping("/api/repertory/queryAll")
    Map<String, Object> queryAll(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @RequestMapping("/api/repertory/insertOne")
    Map<String, Object> insertOne(@RequestBody RepertoryPojo repertoryPojo);

    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @RequestMapping("/api/repertory/updateOne")
    Map<String, Object> updateOne(@RequestBody RepertoryPojo repertoryPojo);

    /**
     * @param repertoryPojo
     * @return
     * @desc 删除一个仓库商品
     */
    @RequestMapping("/api/repertory/deleteOne")
    Map<String, Object> deleteOne(@RequestBody RepertoryPojo repertoryPojo);

    /**
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    @RequestMapping("/api/repertory/getOne")
    Map<String, Object> getOne(@RequestParam("id") Integer id);

    /**
     * 根据id 对仓库数量进行操作 type=1添加 0减少       校验自行在自己的方法里写
     *
     * @param id
     * @param count
     * @param type
     * @return
     */
    @RequestMapping("/api/repertory/countOperation")
    Map<String, Object> countOperation(@RequestParam("id") Integer id,@RequestParam("count") Integer count, @RequestParam("type") Integer type);

}
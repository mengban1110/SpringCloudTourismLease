package cn.doo.repertory.controller;

import cn.doo.framework.entity.pojo.RepertoryPojo;
import cn.doo.repertory.service.RepertoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 仓库库存接口
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/repertory")
public class RepertoryController {

    @Autowired
    private RepertoryService RepertoryServiceFallback;

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit) {
        return RepertoryServiceFallback.queryAll(page, limit);
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(@RequestBody RepertoryPojo repertoryPojo) {
        return RepertoryServiceFallback.insertOne(repertoryPojo);
    }


    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(@RequestBody RepertoryPojo repertoryPojo) {
        return RepertoryServiceFallback.updateOne(repertoryPojo);
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 删除一个仓库商品
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(@RequestBody RepertoryPojo repertoryPojo) {
        return RepertoryServiceFallback.deleteOne(repertoryPojo);
    }

    /**
     * @param id
     * @return
     * @desc 根据id获取一个仓库的库存
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Map<String, Object> getOne(@RequestParam(value = "id", required = false) Integer id) {
        return RepertoryServiceFallback.getOne(id);
    }

    /**
     * @param id
     * @return
     * @desc 根据id去减少/增加库存数量 type = 1增加 type = 2减少
     */
    @RequestMapping(value = "/countOperation", method = RequestMethod.GET)
    public Map<String, Object> countOperation(@RequestParam(value = "id", required = false)  Integer id, @RequestParam(value = "count", required = false)  Integer count, @RequestParam(value = "type", required = false) Integer type) {
        return RepertoryServiceFallback.countOperation(id, count, type);
    }
}

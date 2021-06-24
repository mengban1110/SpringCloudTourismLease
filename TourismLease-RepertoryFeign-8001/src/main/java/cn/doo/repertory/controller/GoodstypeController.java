package cn.doo.repertory.controller;


import cn.doo.framework.entity.pojo.GoodstypePojo;
import cn.doo.repertory.service.GoodstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 库存商品种类
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService GoodstypeServiceFallback;


    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库的种类
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit) {
        return GoodstypeServiceFallback.queryAll(page, limit);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(@RequestBody GoodstypePojo goodstypePojo) {
        return GoodstypeServiceFallback.insertOne(goodstypePojo);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(@RequestBody GoodstypePojo goodstypePojo) {
        return GoodstypeServiceFallback.updateOne(goodstypePojo);
    }


    /**
     * @param goodstypePojo
     * @return
     * @desc 删除一个种类
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(@RequestBody GoodstypePojo goodstypePojo) {
        return GoodstypeServiceFallback.deleteOne(goodstypePojo);
    }

}

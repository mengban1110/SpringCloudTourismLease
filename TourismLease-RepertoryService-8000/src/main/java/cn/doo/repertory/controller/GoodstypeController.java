package cn.doo.repertory.controller;


import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.entity.pojo.GoodstypePojo;
import cn.doo.repertory.service.GoodstypeService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 库存商品种类
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService goodstypeService;


    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库的种类
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return goodstypeService.queryAll(page, limit);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(@RequestBody GoodstypePojo goodstypePojo) {
        if (StringUtils.isEmpty(goodstypePojo.getName())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return goodstypeService.insertOne(goodstypePojo);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(@RequestBody GoodstypePojo goodstypePojo) {
        if (StringUtils.isEmpty(goodstypePojo.getName()) || goodstypePojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return goodstypeService.updateOne(goodstypePojo);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 删除一个种类
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(@RequestBody GoodstypePojo goodstypePojo) {
        if (goodstypePojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }
        return goodstypeService.deleteOne(goodstypePojo.getId());
    }

}

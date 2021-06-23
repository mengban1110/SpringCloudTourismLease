package cn.doo.repertory.controller;

import cn.doo.framework.entity.pojo.RepertoryPojo;
import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.service.RepertoryService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 仓库库存接口
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/repertory")
public class RepertoryController {

    @Autowired
    private RepertoryService repertoryService;

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return repertoryService.queryAll(page, limit);
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(RepertoryPojo repertoryPojo) {
        if (StringUtils.isEmpty(repertoryPojo.getName()) || repertoryPojo.getCount() == null || repertoryPojo.getMoney() == null || repertoryPojo.getUnitdeposit() == null || repertoryPojo.getUnitprice() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return repertoryService.insertOne(repertoryPojo);
    }


    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(RepertoryPojo repertoryPojo) {
        if (StringUtils.isEmpty(repertoryPojo.getName()) || repertoryPojo.getCount() == null || repertoryPojo.getMoney() == null || repertoryPojo.getUnitdeposit() == null || repertoryPojo.getUnitprice() == null || repertoryPojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return repertoryService.updateOne(repertoryPojo);
    }

    /**
     * @param repertoryPojo
     * @return
     * @desc 删除一个仓库商品
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(RepertoryPojo repertoryPojo) {
        if (repertoryPojo.getId() == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }
        return repertoryService.deleteOne(repertoryPojo.getId());
    }
}

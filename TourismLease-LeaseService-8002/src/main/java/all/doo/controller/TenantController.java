package all.doo.controller;


import all.doo.entity.TokenVerify;
import all.doo.entity.pojo.TenantPojo;
import all.doo.service.TenantService;
import all.doo.utils.DooUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 梦伴
 * @desc 租户接口
 * @time 2021-06-01-23:09
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    /**
     * 查询所有
     *
     * @param tokenVerify
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {

        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return tenantService.queryAll(tokenVerify, page, limit);
    }

    /**
     * 新增一个租户信息
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(TokenVerify tokenVerify, TenantPojo tenantPojo) {

        if (StringUtils.isEmpty(tenantPojo.getName()) || StringUtils.isEmpty(tenantPojo.getPhone())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return tenantService.insertOne(tokenVerify, tenantPojo);
    }

    /**
     * 修改一个租户信息
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Map<String, Object> updateOne(TokenVerify tokenVerify, TenantPojo tenantPojo) {

        if (tenantPojo.getId() == null || StringUtils.isEmpty(tenantPojo.getName()) || StringUtils.isEmpty(tenantPojo.getPhone())) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return tenantService.updateOne(tokenVerify, tenantPojo);
    }

    /**
     * 删除一个租户信息
     *
     * @param tokenVerify
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, String id) {

        if (StringUtils.isEmpty(id)) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return tenantService.deleteOne(tokenVerify, id);
    }

}

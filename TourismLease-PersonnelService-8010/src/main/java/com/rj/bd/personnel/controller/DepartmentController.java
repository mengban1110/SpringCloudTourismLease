package com.rj.bd.personnel.controller;

import cn.doo.framework.utils.DooUtils;
import com.rj.bd.personnel.entity.pojo.DepartmentPojo;
import com.rj.bd.personnel.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Desc: Department 的 Controller
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:12
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    public IDepartmentService departmentService;

    /**
     * @desc 获取全部部门
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Map<String, Object> query() {
        return departmentService.query();

    }

    /**
     * @desc 获取全部部门
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        if (page == null) {
            page = 1;

        }

        if (limit == null) {
            limit = 10;

        }

        page = (page-1) * limit;

        return departmentService.queryAll(page, limit);
    }

    /**
     * @desc 根据 ID 查询部门信息
     * @param departmentid
     * @return
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public Map<String, Object> queryById(Integer departmentid) {
        if (departmentid == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return departmentService.queryById(departmentid);

    }

    /**
     * @desc 添加部门
     * @param departmentPojo
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(DepartmentPojo departmentPojo) {
        if (departmentPojo == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return departmentService.add(departmentPojo);

    }

    /**
     * @desc 修改部门信息
     * @param departmentPojo
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Map<String, Object> update(DepartmentPojo departmentPojo) {
        if (departmentPojo == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return departmentService.update(departmentPojo);

    }

    /**
     * @desc 删除部门
     * @param departmentid
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map<String, Object> deleteById(Integer departmentid) {
        if (departmentid == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return departmentService.deleteById(departmentid);

    }

}

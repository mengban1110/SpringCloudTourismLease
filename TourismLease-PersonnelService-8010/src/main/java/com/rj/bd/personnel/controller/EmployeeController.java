package com.rj.bd.personnel.controller;

import cn.doo.framework.utils.DooUtils;
import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import com.rj.bd.personnel.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Desc: Employee 的 Controller
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:12
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * @desc 验证账号密码
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/queryByUsernameAndPassword", method = RequestMethod.GET)
    public Map<String, Object> queryByUsernameAndPassword(String username, String password) {
        return employeeService.queryByUsernameAndPassword(username, password);

    }

    /**
     * @desc 获取头像
     * @param request
     * @param response
     * @param fileName
     * @return
     */
    @RequestMapping(value = "/img/{fileName:.+}", method = RequestMethod.GET)
    public void downloadPhoto(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) throws IOException {
        DooUtils.downloadPhoto(request, response, fileName);
    }

    /**
     * @desc 获取全部员工（姓名模糊）
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/queryByName", method = RequestMethod.GET)
    public Map<String, Object> queryByName(String name, Integer page, Integer limit) {
        if (page == null) {
            page = 1;

        }

        if (limit == null) {
            limit = 10;

        }

        page = (page-1) * limit;

        return employeeService.queryByName(page, limit, name);

    }

    /**
     * @desc 根据 ID 查询员工
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public Map<String, Object> queryById(Integer id) {
        if (id == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return employeeService.queryById(id);

    }

    /**
     * @desc 添加员工信息
     * @param employeePojo
     * @param file
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request) {
        if (employeePojo == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return employeeService.add(employeePojo, file, request);

    }

    /**
     * @desc 修改员工信息
     * @param employeePojo
     * @param file
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Map<String, Object> update(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request) {
        if (employeePojo == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return employeeService.update(employeePojo, file, request);

    }

    /**
     * @desc 删除员工
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map<String, Object> deleteById(Integer id) {
        if (id == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return employeeService.deleteById(id);

    }

}

package com.rj.bd.personnel.service.impl;

import cn.doo.framework.utils.DooUtils;
import com.rj.bd.personnel.dao.EmployeeMapper;
import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import com.rj.bd.personnel.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Desc: Employee 的 Service 的实现类
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:06
 */
@Transactional
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String, Object> queryByName(Integer page, Integer limit, String name) {
        return DooUtils.print(0, "查询成功", employeeMapper.queryByName(name, page, limit), employeeMapper.queryCount(name));

    }

    @Override
    public Map<String, Object> queryById(Integer id) {
        EmployeePojo employeePojo = employeeMapper.selectById(id);

        return DooUtils.print(0, "查询成功", employeePojo, null);

    }

    @Override
    public Map<String, Object> add(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request) {
        String filename = DooUtils.uploadPhotoAndReturnName(file, request);

        employeePojo.setAvatar(filename);

        employeeMapper.insert(employeePojo);

        return DooUtils.print(0, "添加成功", null, null);

    }

    @Override
    public Map<String, Object> update(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request) {
        String filename = DooUtils.uploadPhotoAndReturnName(file, request);

        employeePojo.setAvatar(filename);

        employeeMapper.updateById(employeePojo);

        return DooUtils.print(0, "修改成功", null, null);

    }



    @Override
    public Map<String, Object> deleteById(Integer id) {
        employeeMapper.deleteById(id);

        return DooUtils.print(0, "删除成功", null, null);

    }

    @Override
    public Map<String, Object> queryByUsernameAndPassword(String username, String password) {
        EmployeePojo queryEmployee = employeeMapper.queryByUsernameAndPassword(username, password);
        if (queryEmployee == null) {
            return DooUtils.print(-1, "用户名或密码错误", null, null);

        }

        return DooUtils.print(0, "查询成功", queryEmployee, null);

    }

}

package com.rj.bd.personnel.service;

import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Desc: Employee 的 Service 的接口
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:06
 */
public interface IEmployeeService {

    Map<String, Object> queryByName(Integer page, Integer limit, String name);

    Map<String, Object> queryById(Integer id);

    Map<String, Object> add(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request);

    Map<String, Object> update(EmployeePojo employeePojo, MultipartFile file, HttpServletRequest request);

    Map<String, Object> deleteById(Integer id);

    Map<String, Object> queryByUsernameAndPassword(String username, String password);

}

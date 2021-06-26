package com.rj.bd.personnel.service;

import com.rj.bd.personnel.entity.pojo.DepartmentPojo;

import java.util.Map;

/**
 * @Desc: Department 的 Service 的接口
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:06
 */
public interface IDepartmentService {

    Map<String, Object> queryAll(Integer page, Integer limit);

    Map<String, Object> queryById(Integer departmentid);

    Map<String, Object> add(DepartmentPojo departmentPojo);

    Map<String, Object> update(DepartmentPojo departmentPojo);

    Map<String, Object> deleteById(Integer departmentid);

    Map<String, Object> query();

}

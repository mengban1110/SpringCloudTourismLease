package com.rj.bd.personnel.entity;

import com.rj.bd.personnel.entity.pojo.DepartmentPojo;
import lombok.Data;

/**
 * @Desc: Employee
 * @Auther: LuHoYn
 * @Date: 2021-06-24 16:35
 */
@Data
public class Employee {

    private Integer id;

    private String name;

    private Integer sex;

    private Integer age;

    private String username;

    private String password;

    private String email;

    private DepartmentPojo department;

    private String avatar;

}

package com.rj.bd.personnel.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Desc: Employee 的 Pojo
 * @Auther: LuHoYn
 * @Date: 2021-06-24 16:34
 */
@Data
@TableName(value = "employee")
public class EmployeePojo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer sex;

    private Integer age;

    private String username;

    private String password;

    private String email;

    private Integer departmentid;

    private String avatar;

}

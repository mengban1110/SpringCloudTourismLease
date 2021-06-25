package cn.doo.framework.entity;

import cn.doo.framework.entity.pojo.DepartmentPojo;
import lombok.Data;

/**
 * @Desc:
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

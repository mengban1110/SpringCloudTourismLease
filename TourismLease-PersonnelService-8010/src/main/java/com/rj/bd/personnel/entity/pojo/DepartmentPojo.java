package com.rj.bd.personnel.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Desc: Department 的 Pojo
 * @Auther: LuHoYn
 * @Date: 2021-06-24 16:34
 */
@Data
@TableName(value = "department")
public class DepartmentPojo {

    @TableId(value = "departmentid", type = IdType.AUTO)
    private Integer departmentid;

    private String departmentname;

}

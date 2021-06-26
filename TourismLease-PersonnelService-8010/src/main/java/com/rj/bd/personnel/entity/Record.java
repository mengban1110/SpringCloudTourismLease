package com.rj.bd.personnel.entity;

import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import lombok.Data;

/**
 * @Desc: Record
 * @Auther: LuHoYn
 * @Date: 2021-06-27 1:05
 */
@Data
public class Record {

    private Integer repairid;

    private EmployeePojo employee;

    private String breakinfo;

    private Integer state;
}

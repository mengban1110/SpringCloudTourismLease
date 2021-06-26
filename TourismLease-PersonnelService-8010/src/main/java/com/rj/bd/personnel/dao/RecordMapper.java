package com.rj.bd.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rj.bd.personnel.entity.Record;
import com.rj.bd.personnel.entity.pojo.RecordPojo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Desc: Record 的 Dao
 * @Auther: LuHoYn
 * @Date: 2021-06-26 21:16
 */
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<RecordPojo> {

    @Select("SELECT one.employeeid FROM " +
            "(SELECT id employeeid FROM employee WHERE departmentid = 4) ONE " +
            "LEFT JOIN (SELECT id,COUNT(id) COUNT FROM record WHERE state=0 GROUP BY record.id) two " +
            "ON one.employeeid = two.id " +
            "ORDER BY COUNT " +
            "LIMIT 1")
    Integer queryByState();

    @Results({
            @Result(column = "repairid", property = "repairid"),
            @Result(column = "breakinfo", property = "breakinfo"),
            @Result(column = "state", property = "state"),
            @Result(column = "id", property = "employee", one = @One(select = "com.rj.bd.personnel.dao.EmployeeMapper.selectById"))
    })
    @Select("SELECT * " +
            "FROM record " +
            "LIMIT #{page}, #{limit}")
    List<Record> query(@Param("page") Integer page, @Param("limit") Integer limit);

    @Select("SELECT count(*) " +
            "FROM record")
    Integer queryCount();

}

package com.rj.bd.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rj.bd.personnel.entity.Employee;
import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Desc: Employee 的 Dao
 * @Auther: LuHoYn
 * @Date: 2021-05-31 17:24
 */
@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<EmployeePojo> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "age", property = "age"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
            @Result(column = "departmentid", property = "department", one = @One(select = "com.rj.bd.personnel.dao.DepartmentMapper.selectById")),
            @Result(column = "avatar", property = "avatar")
    })
    @Select("SELECT * " +
            "FROM employee " +
            "WHERE name LIKE '%${name}%' " +
            "LIMIT #{page}, #{limit}")
    List<Employee> queryByName(@Param("name") String name, @Param("page") Integer page, @Param("limit") Integer limit);

    @Select("SELECT count(*) " +
            "FROM employee " +
            "WHERE name like '%${name}%'")
    Integer queryCount(@Param("name") String name);

    @Select("SELECT * " +
            "FROM employee " +
            "WHERE username=#{username} AND password=#{password}")
    EmployeePojo queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}

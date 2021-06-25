package com.rj.bd.personnel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rj.bd.personnel.entity.pojo.DepartmentPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Desc: Department 的 Dao
 * @Auther: LuHoYn
 * @Date: 2021-05-31 17:24
 */
@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<DepartmentPojo> {

}

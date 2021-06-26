package com.rj.bd.facility.dao;

import cn.doo.framework.entity.caigou.Facility;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc
 * @time 2021-06-25-17:50
 */
@Mapper
@Repository("FacilityDao")
public interface FacilityDao {
    @Select("select * from facility f,species s where f.speid = s.speid and f.facname like #{name}")
    public List<Map<String,Object>> queryAll(String name);

    @Select("select * from facility f,species s where f.speid = s.speid and f.facname like #{name}  limit #{start},#{end} ")
    public List<Map<String,Object>> getPage(Map maps);
    @Select("select * from species")
    List<Map<String, Object>> AllZhonglei();
    @Insert("insert into facility(facname,speid,facprice,facimage,facsupplier,factext) values(#{facname},#{speid},#{facprice},#{img},#{facsupplier},#{factext})")
    int insert(@Param("facname") String facname,@Param("speid") String speid,@Param("facprice") String facprice,@Param("img") String img,@Param("facsupplier") String facsupplier,@Param("factext") String factext);
    @Insert("insert into species(spename) values(#{typename})")
    int inserttype(@Param("typename") String typename);
}

package com.rj.bd.purchase.dao;

import cn.doo.framework.entity.caigou.Purchase;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc 采购模块D层
 * @time 2021-06-24-13:11
 */
@Repository("purchasedao")
@Mapper
public interface PurchaseDao {
    @Select("select * from purchase p,facility f,employee e where p.facid = f.facid and p.id = e.id and f.facname like #{name}  and f.facsupplier like #{likeUshop} and e.name like #{likeUpeople}")
    public List<Map<String, Object>> queryAll(@Param("name") String name, @Param("likeUshop") String likeUshop, @Param("likeUpeople") String likeUpeople);

    @Select("select * from purchase p,facility f,employee e where p.facid = f.facid and p.id = e.id and f.facname like #{maps.name}  and f.facsupplier like #{maps.likeUshop} and e.name like #{maps.likeUpeople} limit #{maps.start},#{maps.end} ")
    public List<Map<String, Object>> getPage(@Param("maps") Map maps);

    @Select("select SUM(p.pursum) value,f.facname name from purchase p,facility f,employee e where p.facid = f.facid and p.id = e.id group by f.facid")
    List<Map<String, Object>> queryEcharts();

    @Select("select * from purchase p,facility f,employee e where p.facid = f.facid and p.id = e.id")
    List<Map<String, Object>> queryExcelAll();

    @Select("select * from facility")
    List<Map<String, Object>> queryQixie();

    @Select("select * from facility where facid = #{facid}")
    Map<String, Object> queryOne(String facid);

    @Insert("insert into purchase values(#{purid},#{facility.facid},#{pursum},#{purstart},#{purend},#{employee.id},#{purpriceall},#{purstate})")
    int insert(Purchase purchase);

    @Update("update purchase set  purstate = #{state} where purid = #{purid}")
    int update(@Param("state") String state, @Param("purid") String purid);
}

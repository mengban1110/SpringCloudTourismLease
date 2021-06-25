package all.doo.dao;


import all.doo.entity.Lease;
import all.doo.entity.pojo.LeasePojo;
import all.doo.entity.pojo.TenantPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LeaseMapper extends BaseMapper<LeasePojo> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "leaseinfo", property = "leaseinfo"),
            @Result(column = "createtime", property = "createtime"),
            @Result(column = "endtime", property = "endtime"),
            @Result(column = "deposit", property = "deposit"),
            @Result(column = "rent", property = "rent"),
            @Result(column = "breakinfo", property = "breakinfo"),
            @Result(column = "status", property = "status"),
            @Result(column = "uid", property = "user", one = @One(select = "all.doo.dao.TenantMapper.selectById"))
    })
    @Select("SELECT * FROM lease where id = #{id}")
    Lease queryOne(Integer id);

    @Update("UPDATE repertory SET count=count-#{count} WHERE id=#{id}")
    void reduceCount(Integer count, Integer id);

//    @Select("SELECT lease.* FROM `lease` left JOIN tenant ON lease.uid = tenant.id")
    @Select("SELECT lease.*,tenant.* FROM `lease` left JOIN tenant ON lease.uid = tenant.id ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "leaseinfo", property = "leaseinfo"),
            @Result(column = "createtime", property = "createtime"),
            @Result(column = "endtime", property = "endtime"),
            @Result(column = "deposit", property = "deposit"),
            @Result(column = "rent", property = "rent"),
            @Result(column = "breakinfo", property = "breakinfo"),
            @Result(column = "status", property = "status"),
            @Result(column = "uid", property = "user",one = @One(select = "all.doo.dao.TenantMapper.selectById"))
    })
    Page<Lease> queryAll();
}

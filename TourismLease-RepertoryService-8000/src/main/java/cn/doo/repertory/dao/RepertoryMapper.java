package cn.doo.repertory.dao;

import cn.doo.framework.entity.Repertory;
import cn.doo.framework.entity.pojo.RepertoryPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RepertoryMapper extends BaseMapper<RepertoryPojo> {

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "count", property = "count"),
            @Result(column = "money", property = "money"),
            @Result(column = "unitdeposit", property = "unitdeposit"),
            @Result(column = "unitprice", property = "unitprice"),
            @Result(column = "type", property = "type", one = @One(select = "cn.doo.repertory.dao.GoodstypeMapper.selectById"))
    })
    @Select("SELECT * FROM repertory limit #{page},#{limit}")
    List<Repertory> queryAll(@Param("page") Integer page, @Param("limit") Integer limit);

}

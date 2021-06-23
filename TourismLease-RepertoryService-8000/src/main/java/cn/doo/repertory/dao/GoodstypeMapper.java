package cn.doo.repertory.dao;

import cn.doo.framework.entity.pojo.GoodstypePojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GoodstypeMapper extends BaseMapper<GoodstypePojo> {
}

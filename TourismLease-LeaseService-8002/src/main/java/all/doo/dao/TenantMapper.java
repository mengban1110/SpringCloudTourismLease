package all.doo.dao;


import all.doo.entity.pojo.TenantPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TenantMapper extends BaseMapper<TenantPojo> {
}

package all.doo.service.impl;


import all.doo.dao.TenantMapper;
import all.doo.entity.TokenVerify;
import all.doo.entity.pojo.TenantPojo;
import all.doo.service.TenantService;
import all.doo.utils.DooUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TenantServiceimpl implements TenantService {

    @Autowired
    private TenantMapper tenantMapper;



    /**
     * @param page
     * @param limit
     * @return
     * @desc 查询所有租赁用户
     */
    @Override
    public Map<String, Object> queryAll(TokenVerify tokenVerify, Integer page, Integer limit) {


        /**
         * 查询所有
         */
        IPage<TenantPojo> pagez = new Page<>(page, limit);
        IPage<TenantPojo> tenantPojoIPage = tenantMapper.selectPage(pagez, null);

        return DooUtils.print(0,"请求成功",tenantPojoIPage.getRecords(),tenantPojoIPage.getTotal());
    }


    /**
     * 新增一个租赁用户
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @Override
    public Map<String, Object> insertOne(TokenVerify tokenVerify,TenantPojo tenantPojo) {


        /**
         * 添加租户信息
         */
        tenantMapper.insert(tenantPojo);

        return DooUtils.print(0,"新增成功",null,null);
    }

    /**
     * 修改一个租赁用户
     *
     * @param tokenVerify
     * @param tenantPojo
     * @return
     */
    @Override
    public Map<String, Object> updateOne(TokenVerify tokenVerify, TenantPojo tenantPojo) {


        /**
         * 修改租户信息
         */
        tenantMapper.updateById(tenantPojo);

        return DooUtils.print(0,"修改成功",null,null);

    }

    /**
     * @param id
     * @return
     * @desc 删除一个租赁用户
     */
    @Override
    public Map<String, Object> deleteOne(TokenVerify tokenVerify, String id) {
        tenantMapper.deleteById(id);
        return DooUtils.print(0,"删除成功",null,null);
    }




}

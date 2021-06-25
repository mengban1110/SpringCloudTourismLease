package com.rj.bd.personnel.service.impl;

import cn.doo.framework.utils.DooUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rj.bd.personnel.dao.DepartmentMapper;
import com.rj.bd.personnel.entity.pojo.DepartmentPojo;
import com.rj.bd.personnel.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Desc: Department 的 Service 的实现类
 * @Auther: LuHoYn
 * @Date: 2021-05-31 19:06
 */
@Transactional
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    public DepartmentMapper departmentMapper;

    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        IPage<DepartmentPojo> departmentPojoIPage = new Page<>(page, limit);

        IPage<DepartmentPojo> newDepartmentPojoIPage = departmentMapper.selectPage(departmentPojoIPage, null);

        return DooUtils.print(0, "请求成功", newDepartmentPojoIPage.getRecords(), newDepartmentPojoIPage.getTotal());
    }

    @Override
    public Map<String, Object> queryById(Integer departmentid) {
        DepartmentPojo departmentPojo = departmentMapper.selectById(departmentid);

        return DooUtils.print(0, "查询成功", departmentPojo, null);

    }

    @Override
    public Map<String, Object> add(DepartmentPojo departmentPojo) {
        departmentMapper.insert(departmentPojo);

        return DooUtils.print(0, "添加成功", null, null);

    }

    @Override
    public Map<String, Object> update(DepartmentPojo departmentPojo) {
        departmentMapper.updateById(departmentPojo);

        return DooUtils.print(0, "修改成功", null, null);

    }

    @Override
    public Map<String, Object> deleteById(Integer departmentid) {
        departmentMapper.deleteById(departmentid);

        return DooUtils.print(0, "删除成功", null, null);

    }

}

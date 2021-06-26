package com.rj.bd.personnel.service.impl;

import cn.doo.framework.entity.pojo.Leaseinfo;
import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.service.RepertoryService;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rj.bd.personnel.dao.EmployeeMapper;
import com.rj.bd.personnel.dao.RecordMapper;
import com.rj.bd.personnel.entity.pojo.EmployeePojo;
import com.rj.bd.personnel.entity.pojo.RecordPojo;
import com.rj.bd.personnel.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc: Record 的 Service 的实现类
 * @Auther: LuHoYn
 * @Date: 2021-06-26 21:19
 */
@Transactional
@Service
public class RecordServiceImpl implements IRecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RepertoryService RepertoryServiceFallback;

    @Override
    public Map<String, Object> add(List<Leaseinfo> leaseinfos) {
        // 获取员工 id
        int id = queryByState();

        // 添加维修记录
        RecordPojo recordPojo = new RecordPojo();

        recordPojo.setId(id);

        String json = JSONUtil.toJsonStr(leaseinfos);

        recordPojo.setBreakinfo(json);

        recordPojo.setState(0);

        recordMapper.insert(recordPojo);

        // 通过 id 获取员工信息返回
        EmployeePojo employeePojo = employeeMapper.selectById(id);

        Map<String, Object> map = new HashMap<>(2);

        map.put("email", employeePojo.getEmail());

        map.put("id", employeePojo.getId());

        return DooUtils.print(0, "生成维修记录成功", map, null);

    }

    @Override
    public Map<String, Object> update(Integer repairid) throws IOException {
        RecordPojo recordPojo = new RecordPojo();

        recordPojo.setRepairid(repairid);

        recordPojo.setState(1);

        recordMapper.updateById(recordPojo);

        recordPojo = recordMapper.selectById(repairid);

        ObjectMapper objectMapper = new ObjectMapper();

        List<Leaseinfo> leaseinfos = objectMapper.readValue(recordPojo.getBreakinfo(), new TypeReference<List<Leaseinfo>>(){});

        for (Leaseinfo leaseinfo: leaseinfos) {
            RepertoryServiceFallback.countOperation(leaseinfo.getId(), leaseinfo.getNumber().size(), 1);

        }

        return DooUtils.print(0, "修改维修状态成功", null, null);

    }

    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        return DooUtils.print(0, "查询", recordMapper.query(page, limit), recordMapper.queryCount());

    }

    /**
     * @desc 获取任务最少维修部门员工
     * @return
     */
    private Integer queryByState() {

        return recordMapper.queryByState();

    }

}

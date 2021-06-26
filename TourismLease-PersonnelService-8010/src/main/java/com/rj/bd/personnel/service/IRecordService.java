package com.rj.bd.personnel.service;

import cn.doo.framework.entity.pojo.Leaseinfo;
import com.rj.bd.personnel.entity.pojo.RecordPojo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Desc: Record 的 Service 的接口
 * @Auther: LuHoYn
 * @Date: 2021-06-26 21:18
 */
public interface IRecordService {

    Map<String, Object> add(List<Leaseinfo> leaseinfos);

    Map<String, Object> update(Integer repairid) throws IOException;

    Map<String, Object> queryAll(Integer page, Integer limit);

}

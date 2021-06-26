package com.rj.bd.personnel.controller;

import cn.doo.framework.entity.pojo.Leaseinfo;
import cn.doo.framework.utils.DooUtils;
import com.rj.bd.personnel.entity.pojo.RecordPojo;
import com.rj.bd.personnel.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Desc: Record 的 Controller
 * @Auther: LuHoYn
 * @Date: 2021-06-26 21:15
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private IRecordService repairService;

    /**
     * @desc 获取维修信息并生成维修记录
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody List<Leaseinfo> leaseinfos) {
        if (leaseinfos == null) {
            return DooUtils.print(-1, "没有损坏信息", null, null);

        }

        return repairService.add(leaseinfos);

    }

    /**
     * @desc 修改维修状态
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Map<String, Object> update(Integer repairid) throws IOException {
        if (repairid == null) {
            return DooUtils.print(-2, "参数错误", null, null);

        }

        return repairService.update(repairid);

    }

    /**
     * @desc 获取所有维修记录
     * @return
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        if (page == null) {
            page = 1;

        }

        if (limit == null) {
            limit = 10;

        }

        page = (page-1) * limit;

        return repairService.queryAll(page, limit);

    }

}

package cn.doo.repertory.service;


import cn.doo.repertory.entity.pojo.GoodstypePojo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "TourismLease-RepertoryService")
public interface GoodstypeService {
    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
     */
    @RequestMapping("/api/goodstype/queryAll")
    Map<String, Object> queryAll(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit);

    /**
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @RequestMapping("/api/goodstype/insertOne")
    Map<String, Object> insertOne(@RequestBody GoodstypePojo goodstypePojo);

    /**
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @RequestMapping("/api/goodstype/updateOne")
    Map<String, Object> updateOne(@RequestBody GoodstypePojo goodstypePojo);

    /**
     * @param goodstypePojo
     * @return
     * @desc 删除一个种类
     */
    @RequestMapping("/api/goodstype/deleteOne")
    Map<String, Object> deleteOne(@RequestBody GoodstypePojo goodstypePojo);
}

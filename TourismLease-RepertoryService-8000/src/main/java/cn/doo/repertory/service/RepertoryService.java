package cn.doo.repertory.service;


import cn.doo.framework.entity.pojo.RepertoryPojo;

import java.util.Map;

public interface RepertoryService {

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库商品
     */
    Map<String, Object> queryAll(Integer page, Integer limit);

    /**
     * @param repertoryPojo
     * @return
     * @desc 新增一个仓库商品
     */
    Map<String, Object> insertOne(RepertoryPojo repertoryPojo);

    /**
     * @param repertoryPojo
     * @return
     * @desc 修改一个仓库商品
     */
    Map<String, Object> updateOne(RepertoryPojo repertoryPojo);

    /**
     * @param id
     * @return
     * @desc 删除一个仓库商品
     */
    Map<String, Object> deleteOne(Integer id);
}

package cn.doo.repertory.service;


import cn.doo.repertory.entity.pojo.RepertoryPojo;

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

    /**
     * @param id
     * @return
     * @desc 根据id获取库存信息
     */
    Map<String, Object> getOne(Integer id);


    /**
     * @param id
     * @return
     * @desc 根据id操作数量
     */
    Map<String, Object> countOperation(Integer id, Integer count, Integer type);

}

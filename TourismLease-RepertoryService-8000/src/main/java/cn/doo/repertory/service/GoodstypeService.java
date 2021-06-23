package cn.doo.repertory.service;


import cn.doo.framework.entity.pojo.GoodstypePojo;

import java.util.Map;

public interface GoodstypeService {
    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
     */
    Map<String, Object> queryAll(Integer page, Integer limit);

    /**
     * @desc 新增一个种类
     * @param goodstypePojo
     * @return
     */
    Map<String, Object> insertOne(GoodstypePojo goodstypePojo);

    /**
     * @desc 修改一个种类
     * @param goodstypePojo
     * @return
     */
    Map<String, Object> updateOne(GoodstypePojo goodstypePojo);

    /**
     * @desc 删除一个种类
     * @param id
     * @return
     */
    Map<String,Object> deleteOne(Integer id);
}

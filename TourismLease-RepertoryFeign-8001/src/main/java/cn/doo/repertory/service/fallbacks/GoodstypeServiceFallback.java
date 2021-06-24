package cn.doo.repertory.service.fallbacks;

import cn.doo.framework.entity.pojo.GoodstypePojo;
import cn.doo.repertory.service.GoodstypeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-24-2:55
 */
@Component("GoodstypeServiceFallback")//通过@Compontent将当前这个类设定为M层的实现类，且名字为feginUserFallBackService，好在C能能实现自动装配
public class GoodstypeServiceFallback implements GoodstypeService {
    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
     */
    @Override
    public Map<String, Object> queryAll(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        return null;
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @Override
    public Map<String, Object> insertOne(@RequestBody GoodstypePojo goodstypePojo) {
        return null;
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @Override
    public Map<String, Object> updateOne(@RequestBody GoodstypePojo goodstypePojo) {
        return null;
    }

    /**
     * @return
     * @desc 删除一个种类
     */
    @Override
    public Map<String, Object> deleteOne(@RequestBody GoodstypePojo goodstypePojo) {
        return null;
    }
}

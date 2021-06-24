package cn.doo.repertory.service.impl;

import cn.doo.framework.utils.DooUtils;
import cn.doo.repertory.dao.GoodstypeMapper;
import cn.doo.repertory.entity.pojo.GoodstypePojo;
import cn.doo.repertory.service.GoodstypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodstypeServiceimpl implements GoodstypeService {

    @Autowired
    private GoodstypeMapper goodstypeMapper;

    /**
     * @param page
     * @param limit
     * @return
     * @desc 获取所有仓库种类
     */
    @Override
    public Map<String, Object> queryAll(Integer page, Integer limit) {
        IPage<GoodstypePojo> pagez = new Page<>(page, limit);
        IPage<GoodstypePojo> goodstypePojoIPage = goodstypeMapper.selectPage(pagez, null);
        return DooUtils.print(0, "请求成功", goodstypePojoIPage.getRecords(), goodstypePojoIPage.getTotal());
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 新增一个种类
     */
    @Override
    public Map<String, Object> insertOne(GoodstypePojo goodstypePojo) {
        goodstypePojo.setCreatetime(new Date());
        goodstypeMapper.insert(goodstypePojo);
        return DooUtils.print(0, "添加成功", null, null);
    }

    /**
     * @param goodstypePojo
     * @return
     * @desc 修改一个种类
     */
    @Override
    public Map<String, Object> updateOne(GoodstypePojo goodstypePojo) {
        goodstypeMapper.updateById(goodstypePojo);
        return DooUtils.print(0, "修改成功", null, null);
    }

    /**
     * @param id
     * @return
     * @desc 删除一个种类
     */
    @Override
    public Map<String, Object> deleteOne(Integer id) {
        goodstypeMapper.deleteById(id);
        return DooUtils.print(0, "删除成功", null, null);
    }

}

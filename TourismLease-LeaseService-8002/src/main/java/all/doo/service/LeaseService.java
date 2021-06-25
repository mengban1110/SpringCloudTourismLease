package all.doo.service;


import all.doo.entity.Leaseinfo;
import all.doo.entity.TokenVerify;
import all.doo.entity.pojo.LeaseinfoPojo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LeaseService {

    /**
     * 获取所有租赁订单信息
     *
     * @param phone
     * @param page
     * @param limit
     * @return
     */
    Map<String, Object> queryAll(   String phone, Integer page, Integer limit) throws Exception;


    /**
     * @param uid
     * @return
     * @desc 新增一个订单信息
     */
    Map<String, Object> insertOne(   List<LeaseinfoPojo> leaseinfo, Integer uid) throws Exception;

    /**
     * 结束租赁
     *
     * @param leaseinfo
     * @return
     * @throws Exception
     */
    Map<String, Object> updateOne(   List<Leaseinfo> leaseinfo, Integer id) throws Exception;

    /**
     * 删除订单
     * @param id
     * @return
     */
    Map<String, Object> deleteOne(   Integer id);

    /**
     * 下载收据
     * @param id
     * @return
     */
    Map<String, Object> download(   Integer id, HttpServletResponse response) throws IOException;
}

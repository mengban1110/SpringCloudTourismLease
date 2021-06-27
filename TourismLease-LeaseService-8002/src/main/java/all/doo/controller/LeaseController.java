package all.doo.controller;


import cn.doo.framework.entity.pojo.Leaseinfo;
import all.doo.entity.pojo.LeaseinfoPojo;
import all.doo.service.LeaseService;
import all.doo.utils.DooUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 租赁订单接口
 */
@RequestMapping("/lease")
@CrossOrigin("*")
@RestController
@RefreshScope
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @Value("${username}")
    private String username;

    /**
     * @param page
     * @param limit
     * @param phone
     * @return
     */
    @RequestMapping("/queryAll")
    public Map<String, Object> queryAll(Integer page, Integer limit, String phone) throws Exception {

        System.out.println(username);
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }

        return leaseService.queryAll(phone, page, limit);
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public Map<String, Object> insertOne(@RequestBody List<LeaseinfoPojo> leaseinfoPojo, @RequestParam(name = "uid") Integer uid) throws Exception {

        if (leaseinfoPojo.size() == 0 || leaseinfoPojo == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        if (uid == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.insertOne(leaseinfoPojo, uid);
    }

    @RequestMapping(value = "/updateOne")
    public Map<String, Object> updateOne(@RequestBody List<Leaseinfo> leaseinfos, Integer id) throws Exception {

        if (leaseinfos.size() == 0 || leaseinfos == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.updateOne(leaseinfos, id);
    }

    @RequestMapping(value = "/deleteOne")
    public Map<String, Object> deleteOne(Integer id) throws Exception {


        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.deleteOne(id);
    }


    /**
     * 收据条
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public Map<String, Object> download(Integer id, HttpServletResponse response) throws Exception {


        if (id == null) {
            return DooUtils.print(-2, "参数异常", null, null);
        }

        return leaseService.download(id, response);
    }


}

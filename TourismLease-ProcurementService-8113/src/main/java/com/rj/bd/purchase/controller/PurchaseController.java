package com.rj.bd.purchase.controller;

import cn.doo.framework.entity.caigou.Employee;
import cn.doo.framework.entity.caigou.Facility;
import cn.doo.framework.entity.caigou.Purchase;
import cn.doo.repertory.service.RepertoryService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.word.Word07Writer;
import com.rj.bd.purchase.service.PurchaseService;
import com.rj.bd.utils.Dates;
import com.rj.bd.utils.SnowFlakeUtil;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc 采购模块C层
 * @time 2021-06-24-13:10
 */
@RestController
@RequestMapping("/purchasecontroller")
@CrossOrigin("*")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    RepertoryService repertoryService;

    @RequestMapping("/queryALL")
    public Map<String, Object> queryAll(@RequestParam Map maps, String name, String likeUshop, String likeUpeople) {
        System.out.println("Purchase:queryALLPage()---->" + name + likeUshop + likeUpeople);
        List<Map<String, Object>> Page = purchaseService.getPage(maps, name, likeUshop, likeUpeople);
        List<Map<String, Object>> list = purchaseService.queryAll(name, likeUshop, likeUpeople);
        int count = list.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", count);
        map.put("code", 0);
        map.put("msg", "获取Purchase信息成功");
        map.put("data", Page);
        return map;
    }

    /**
     * @desc:可视化
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/keshihua", method = RequestMethod.POST)
    public Map<String, Object> keshihua() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = purchaseService.queryEcharts();
        map.put("code", 200);
        map.put("msg", "获取成功");
        map.put("data", list);
        return map;
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/querybiao", method = RequestMethod.GET)
    public Map<String, Object> excel(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Map<String, Object>> list = purchaseService.queryExcelAll();
        System.out.println("123456");
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("code", "200");
        json.put("msg", "请求成功");
        json.put("data", list);

        return json;
    }

    /**
     * @desc:目的是为了生成申请表
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/createword", method = RequestMethod.POST)
    public Map<String, Object> createword(String username, String facsupplier, String facname, String pursum, String facprice, String purpriceall, String factext, String purid) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(username + facsupplier + facname);
        Word07Writer writer = new Word07Writer();

        // 添加段落（标题）
        // writer.addText(new Font("宋体", Font.PLAIN, 18),  "采购申请书");
        writer.addText(ParagraphAlignment.CENTER, new Font("宋体", Font.PLAIN, 18), "采购申请书");

        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 12), "    尊敬的领导！我是采购管理员：" + username + "，",
                "本次我要采购的产品是来自：" + facsupplier + "公司的商品：" + facname + "。由于库存损耗及用户量的增加，我们想要采购的数量是：" + pursum + "个，这种商品的单价是：" + facprice + "元，共计：" + purpriceall + "元。",
                "这个商品对于用户的总体评价来看：" + factext + "。本次操作的采购单号是：" + purid + "。望领导批准！");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "                                            申请时间：" + Dates.CurrentTime() + "");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "                                            申请人：" + username + "(采购员)");
        writer.addText(new Font("宋体", Font.PLAIN, 12), "                                            同意申请签字及盖章：");
        // 写出到文件
        writer.flush(FileUtil.file("e:/wordWrite.docx"));
        // 关闭
        writer.close();

        map.put("code", 200);
        map.put("msg", "生成采购申请书成功");


        return map;
    }

    /**
     * @desc:获取到所有的商品
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/queryqiju", method = RequestMethod.POST)
    public Map<String, Object> queryqiju() {
        Map<String, Object> map = new HashMap<>();
        //用于订单模块的器械下拉列表！获取所有的器械
        List<Map<String, Object>> list_qiju = purchaseService.queryqixies();
        System.out.println(list_qiju);
        map.put("code", 200);
        map.put("msg", "获取成功");
        map.put("data", list_qiju);
        return map;
    }

    /**
     * @desc: 采购单
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map<String, Object> insert(String token, String username, String purend, String pursum, String facid) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(purend);
        System.out.println(pursum);
        System.out.println(facid);
        System.out.println(username);

        Purchase purchase = new Purchase();//采购单实体类
        String purid = "" + SnowFlakeUtil.getId();
        purchase.setPurid(purid);//雪花算法生成采购单编号
        String date = Dates.CurrentTime();//工具类生成当前日期
        purchase.setPurstart(date);//开始日期
        purchase.setPurend(purend);//结束日期
        purchase.setPursum(pursum);//采购数量
        purchase.setPurstate("未完成");


        Facility facility = new Facility();//哪个器械
        int facids = Integer.valueOf(facid);
        facility.setFacid(facids);
        purchase.setFacility(facility);

        Employee employee = new Employee();//采购人
        employee.setId(1001);
        purchase.setEmployee(employee);

        //目的是获取订单总价
        Map<String, Object> map_price = purchaseService.queryByOnePrice(facid);//拿到单价
        String price = (String) map_price.get("facprice");
        Integer facprice = Integer.valueOf(price);//单价
        Integer num = Integer.valueOf(pursum);//数量
        Integer allprice = facprice * num;//总价
        purchase.setPurpriceall("" + allprice);

        int a = purchaseService.insertpur(purchase);
        if (a > 0) {
            map.put("code", 200);
            map.put("msg", "添加成功");
        }
        {
            map.put("code", -1);
            map.put("msg", "添加失败");
        }
        return map;
    }

    /**
     * @desc:审核通过
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public Map<String, Object> audit(String purid, Integer pursum, Integer facid) {
        Map<String, Object> map = new HashMap<>();
        String state = "已完成";
        int a = purchaseService.update(state, purid);
        System.out.println("查看一下获取的facid+pursum" + facid + "-" + pursum);
        repertoryService.countOperation(facid, pursum, 1);
        return map;
    }


}

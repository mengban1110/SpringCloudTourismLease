package com.rj.bd.facility.controller;

import cn.doo.framework.entity.caigou.Facility;
import cn.doo.framework.entity.pojo.GoodstypePojo;
import cn.doo.framework.entity.pojo.RepertoryPojo;
import cn.doo.repertory.service.GoodstypeService;
import cn.doo.repertory.service.RepertoryService;
import com.rj.bd.facility.service.FacilityService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc
 * @time 2021-06-25-17:49
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/facility")
public class FacilityController {
    @Autowired
    FacilityService facilityService;
    @Autowired
    RepertoryService repertoryService;
    @Autowired
    GoodstypeService goodstypeService;

    @RequestMapping(value ="/queryALL",method = RequestMethod.GET)
    public Map<String, Object> queryALLPage(@RequestParam Map maps, String names){
        System.out.println("获取前端传来的name值"+names);
        System.out.print("FacilityController:queryALLPage()");
        String name = null;

        if (names!=null){
            boolean status = names.contains(":");
            if(status){
                String[] sourceStrArray = names.split(":");//切割字符串
                name = sourceStrArray[1];
            }else {
                name = names;
            }
        }
        System.out.println("分割后的name值"+name);
        List<Map<String,Object>> Page=facilityService.getPage(maps,name);
        List<Map<String,Object>> list=facilityService.queryAll(name);
        int count=list.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count",count);
        map.put("code",0);
        map.put("msg", "获取Facility信息成功");
        map.put("data", Page);
        return map;
    }
    /**
     * @desc:先添加图片
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/saveimage",method = RequestMethod.POST)
    public Map<String,Object> saveimages(MultipartFile file, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        if (!file.isEmpty()) {
            //文件的真实名称
            String realFilename = file.getOriginalFilename();
            System.out.println("真实名字" + realFilename);
            //获取文件格式后缀名
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            // 取当前时间戳作为文件名
            String filename = System.currentTimeMillis() + type;
            //String path = request.getSession().getServletContext().getRealPath("/upload/" + filename);// 存放位置
            String path = "/ideaworkspace/springcloudAll/Project_SpringBootHtml/images/" + filename;
            //D:/ideaworkspace/Project_SpringBootHtml/Project_SpringBootHtml/images/
            //D:/Web/Project_SpringBootHtml/images/
            //D:/ideaworkspace/springcloudAll/Project_SpringBootHtml/images/
            System.out.println("-------------------->" + path);


            File destFile = new File(path);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);// 复制临时文件到指定目录下
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("code",200);
            map.put("msg","获取成功");
            map.put("fileName",filename);
        } else {
        }
        return  map;
    }

    /**
     * @desc:获取全部的种类
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/getZhongLei",method = RequestMethod.GET)
    public Map<String,Object> Zhonglei(){
        Map<String,Object> map = new HashMap<>();
     List<Map<String,Object>> list = facilityService.queryALLZhonglei();
     if (list.size()>0){
         map.put("code",200);
         map.put("msg","获取成功");
         map.put("data",list);
     }else {
         map.put("code",0);
         map.put("msg","cuowu");
     }
        return  map;
    }

    /**
     * @desc:添加新商品
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map<String,Object> add(String facname,String speid,String facprice,String facsupplier,String factext,String img){
        Map<String,Object> map = new HashMap<>();
        System.out.println(facname+speid+facprice+facsupplier+factext+img);
        int a = facilityService.insert(facname,speid,facprice,img,facsupplier,factext);
        if (a>0){
            map.put("code",200);
            map.put("msg","成功");
        }else {
            map.put("code",-1);
        }
        RepertoryPojo repertoryPojo = new RepertoryPojo();
        repertoryPojo.setName(facname);
        repertoryPojo.setCount(0);
        repertoryPojo.setMoney(Integer.valueOf(facprice));
        repertoryPojo.setUnitdeposit(0);
        repertoryPojo.setUnitprice(0);
        repertoryPojo.setType(Integer.valueOf(speid));

        repertoryService.insertOne(repertoryPojo);

        return  map;
    }
    /**
     * @desc:添加种类
     */
    @CrossOrigin("*")
    @RequestMapping(value = "/addtype",method = RequestMethod.POST)
    public Map<String,Object> addtype(String typename){
        Map<String,Object> map = new HashMap<>();
        System.out.println("前台"+typename);
        int a = facilityService.insertType(typename);
        if (a>0){
            map.put("code",200);
            map.put("msg","添加成功");
        }else {
            map.put("code",-1);
            map.put("msg","添加失败");
        }
        GoodstypePojo goodstypePojo = new GoodstypePojo();
        goodstypePojo.setName(typename);
        goodstypeService.insertOne(goodstypePojo);
        return  map;
    }

}

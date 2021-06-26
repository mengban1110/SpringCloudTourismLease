package com.rj.bd.facility.service;

import cn.doo.framework.entity.caigou.Facility;
import com.rj.bd.facility.dao.FacilityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc
 * @time 2021-06-25-17:50
 */
@Service
public class FacilityService {
    @Autowired
    FacilityDao facilityDao;
    public List<Map<String,Object>> queryAll(String name) {
        if (name == null) {
            name = "";
        }
        return facilityDao.queryAll("%"+name+"%");
    }

    @Transactional
    public List<Map<String,Object>> getPage(Map maps, String name) {
        int pageNo=maps.get("pageNo")==null?1:Integer.valueOf(maps.get("pageNo")+"");
        int pageSize=maps.get("pageSize")==null?5:Integer.valueOf(maps.get("pageSize")+"");
//		name= (String) maps.get("name");
        if (name == null) {
            name = "";
        }
        System.out.println("facility--->M---->最后"+name);
        maps.put("name","%"+name+"%");
        maps.put("start",(pageNo-1)*pageSize);
        maps.put("end",pageSize);


        return facilityDao.getPage(maps);
    }

    public List<Map<String, Object>> queryALLZhonglei() {
        return facilityDao.AllZhonglei();
    }

    public int insert(String facname, String speid, String facprice, String img, String facsupplier, String factext) {
        return facilityDao.insert(facname,speid,facprice,img,facsupplier,factext);
    }

    public int insertType(String typename) {
        return facilityDao.inserttype(typename);
    }
}

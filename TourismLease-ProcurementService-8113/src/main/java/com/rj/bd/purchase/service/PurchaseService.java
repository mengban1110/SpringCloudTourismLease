package com.rj.bd.purchase.service;

import cn.doo.framework.entity.caigou.Purchase;
import com.rj.bd.purchase.dao.PurchaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Y
 * @desc
 * @time 2021-06-24-13:12
 */
@Service("purchaseservice")
public class PurchaseService {
    @Autowired
    PurchaseDao purchaseDao;

    public List<Map<String,Object>> queryAll(String name, String likeUshop, String likeUpeople) {
        if (name == null) {
            name = "";
        }
        if (likeUshop == null) {
            likeUshop = "";
        }
        if (likeUpeople == null) {
            likeUpeople = "";
        }
        name="%"+name+"%";
        likeUshop="%"+likeUshop+"%";
        likeUpeople="%"+likeUpeople+"%";
        System.out.println("-----"+name+"\t"+likeUshop+"\t"+likeUpeople+"\t");
        return purchaseDao.queryAll(name,likeUshop,likeUpeople);
    }

    @Transactional
    public List<Map<String,Object>> getPage(Map maps, String name,String likeUshop,String likeUpeople) {
        int pageNo=maps.get("pageNo")==null?1:Integer.valueOf(maps.get("pageNo")+"");
        int pageSize=maps.get("pageSize")==null?5:Integer.valueOf(maps.get("pageSize")+"");
        name= (String) maps.get("name");
        if (name == null) {
            name = "";
        }
        if (likeUshop == null) {
            likeUshop = "";
        }
        if (likeUpeople == null) {
            likeUpeople = "";
        }
        maps.put("name","%"+name+"%");
        maps.put("likeUshop","%"+likeUshop+"%");
        maps.put("likeUpeople","%"+likeUpeople+"%");
        maps.put("start",(pageNo-1)*pageSize);
        maps.put("end",pageSize);
        System.out.println(maps.get("name")+"\t"+maps.get("likeUshop")+"\t"+maps.get("likeUpeople")+"\t"+maps.get("start")+"\t"+maps.get("end")+"\t");
        return purchaseDao.getPage(maps);
    }

    public List<Map<String, Object>> queryEcharts() {
        return purchaseDao.queryEcharts();
    }

    public List<Map<String, Object>> queryExcelAll() {
        return purchaseDao.queryExcelAll();
    }

    public List<Map<String, Object>> queryqixies() {
        return purchaseDao.queryQixie();
    }

    public Map<String, Object> queryByOnePrice(String facid) {
        return purchaseDao.queryOne(facid);
    }

    public int insertpur(Purchase purchase) {
        return purchaseDao.insert(purchase);
    }

    public int update(String state, String purid) {
        return purchaseDao.update(state,purid);
    }
}

package com.rj.bd.service;

import cn.doo.framework.entity.pojo.Leaseinfo;
import com.rj.bd.service.fallbacks.FeignPersonnelFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(value = "TOURISMLEASE-PERSONNELSERVICE", fallback = FeignPersonnelFallbackService.class)
public interface IFeignPersonnelService {

    @GetMapping("/employee/queryByUsernameAndPassword")
    Map<String, Object> queryByUsernameAndPassword(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/record/add")
    Map<String, Object> add(@RequestBody List<Leaseinfo> leaseinfos);

}

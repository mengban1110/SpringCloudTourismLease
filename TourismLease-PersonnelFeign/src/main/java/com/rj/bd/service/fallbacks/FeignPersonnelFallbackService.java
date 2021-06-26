package com.rj.bd.service.fallbacks;

import cn.doo.framework.entity.pojo.Leaseinfo;
import cn.doo.framework.utils.DooUtils;
import com.rj.bd.service.IFeignPersonnelService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("feignLoginService")
public class FeignPersonnelFallbackService implements IFeignPersonnelService {

    @Override
    public Map<String, Object> queryByUsernameAndPassword(String username, String password) {
        return DooUtils.print(500, "服务暂不可用", null, null);
    }

    @Override
    public Map<String, Object> add(List<Leaseinfo> leaseinfos) {
        return DooUtils.print(500, "服务暂不可用", null, null);

    }

}

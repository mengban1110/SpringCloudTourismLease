package com.rj.bd.login.controller;

import cn.doo.framework.entity.pojo.EmployeePojo;
import cn.doo.framework.utils.DooUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rj.bd.service.IFeignPersonnelService;
import com.rj.bd.login.service.ILoginSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public IFeignPersonnelService feignLoginService;

    @Autowired
    public ILoginSerive loginSerive;

    /**
     * @desc 发送验证码
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public Map<String, Object> sendCode(String username, String password) throws MessagingException, IOException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return DooUtils.print(-1, "用户名或密码错误", null, null);
        }

        Map<String, Object> map = feignLoginService.queryByUsernameAndPassword(username, password);

        if (map.get("data") == null) {
            return DooUtils.print(-1, "用户名或密码错误", null, null);

        }

        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = mapper.writeValueAsString(map.get("data"));

        EmployeePojo employeePojo = mapper.readValue(jsonStr, EmployeePojo.class);

        return loginSerive.sendCode(username, password, employeePojo);

    }

    /**
     * @desc 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password, String code) throws IOException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return DooUtils.print(-1, "用户名或密码错误", null, null);

        }
        if (StringUtils.isEmpty(code)) {
            return DooUtils.print(-3, "验证码错误", null, null);

        }

        Map<String, Object> map = feignLoginService.queryByUsernameAndPassword(username, password);

        if (map.get("data") == null) {
            return DooUtils.print(-1, "用户名或密码错误", null, null);

        }

        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = mapper.writeValueAsString(map.get("data"));

        EmployeePojo employeePojo = mapper.readValue(jsonStr, EmployeePojo.class);

        return loginSerive.login(username, password, code, employeePojo);

    }


}

package com.rj.bd.login.service;

import cn.doo.framework.entity.pojo.EmployeePojo;
import cn.doo.framework.utils.DooUtils;
import com.rj.bd.login.utils.FkEmailUtils;
import com.rj.bd.login.utils.redis.RedisUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "loginSerive")
public class LoginServiceImpl implements ILoginSerive {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FkEmailUtils emailUtils;

    @Override
    public Map<String, Object> sendCode(String username, String password, EmployeePojo employeePojo) throws MessagingException {


        String verificationCode = "verify:" + employeePojo.getUsername();

        // 判断是否已经发送过验证码
        String value = redisUtil.get(verificationCode);

        // 没有发送验证码
        if (StringUtils.isEmpty(value)) {
            // 获取验证码
            String checkCode = DooUtils.makeCode(4);

            // 设置验证码有效期 5 分钟
            redisUtil.setEx(verificationCode, checkCode, 5, TimeUnit.MINUTES);

            //发送验证码
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    emailUtils.sendVerifyEmail(employeePojo.getEmail(), employeePojo.getEmail(), checkCode);
                }
            }).run();

            return DooUtils.print(0, "验证码发送成功", null, null);

        }

        return DooUtils.print(-2, "请勿频繁发送验证码，稍后再试", null, null);

    }

    @Override
    public Map<String, Object> login(String username, String password, String code, EmployeePojo employeePojo) {
        // 从 redis 中拿取邮箱验证码
        String value = redisUtil.get("verify:" + username);

        // 值为空则缓存失效，反之成功
        if (!(StringUtils.isEmpty(value))) {
            if (code.equals(value)) {
                // token
                String token = DooUtils.getToken(employeePojo.getEmail());

                // 存 token 到 redis ,有效期 1 小时
                redisUtil.setEx("token:" + username, token, 1, TimeUnit.HOURS);

                HashMap<String, Object> hashMap = new HashMap<>(3);

                hashMap.put("token", token);

                hashMap.put("username", employeePojo.getUsername());

                hashMap.put("avatar", employeePojo.getAvatar());

                return DooUtils.print(0, "登录成功", hashMap, null);

            } else {
                return DooUtils.print(-3, "验证码错误", null, null);

            }

        } else {
            return DooUtils.print(-2, "验证码失效", null, null);

        }

    }

}

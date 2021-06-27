package cn.doo.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-27-11:37
 */
@FeignClient(value = "TourismLease-Kafka")
@Component
public interface EmailService {

    /**
     * 发送邮件
     *
     * @param email
     * @param code
     * @param type
     */
    @RequestMapping("/message/send")
    void sendEmail(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("type") Integer type);

}

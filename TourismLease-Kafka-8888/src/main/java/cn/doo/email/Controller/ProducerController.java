package cn.doo.email.Controller;

import cn.doo.framework.entity.pojo.Verify;
import cn.doo.framework.utils.DooUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping("/message/send")
    public String send(String email, String code, Integer type) throws JsonProcessingException {
        Verify verify = new Verify(email, code, type, DooUtils.getToken(System.currentTimeMillis() + ""));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(verify);

        //使用kafka模板发送信息
        kafkaTemplate.send("javatest", json);
        return "success";
    }

}

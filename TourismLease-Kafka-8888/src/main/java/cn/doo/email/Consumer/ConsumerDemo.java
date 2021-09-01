package cn.doo.email.Consumer;

import cn.doo.email.FkEmailUtils;
import cn.doo.email.redis.RedisUtil;
import cn.doo.framework.entity.pojo.Verify;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class ConsumerDemo {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FkEmailUtils emailUtils;


    /**
     * 定义此消费者接收topics = "demo"的消息，与controller中的topic对应上即可
     *
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "javatest")
    public void listen(ConsumerRecord<?, ?> record) throws IOException, MessagingException {
        System.out.printf("topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());

        //获取数据
        String jsonString = record.value().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Verify verify = objectMapper.readValue(jsonString, Verify.class);

        //获取唯一标识id 开始验证是否重复消费
        String uuid = verify.getUuid();

        Boolean hasKey = redisUtil.hasKey("email:" + uuid);
        if (hasKey) {
            //重复消费
            System.out.println("uuid = " + uuid + " 已经存在 , 成功拦截重复消费");
        } else {
            //执行消费

            //0 : 登录验证码 , 1 : 发送维修提示
            Integer type = verify.getType();
            if (type == 0) {
                emailUtils.sendVerifyEmail(verify.getEmail(), verify.getEmail(), verify.getCode());
            } else {
                emailUtils.sendCheckEmail(verify.getEmail());
            }

            //kafka默认保存7天日志 所以要高出7天
            redisUtil.setEx("email:" + verify.getUuid(), "success", 8, TimeUnit.DAYS);

        }

    }
}

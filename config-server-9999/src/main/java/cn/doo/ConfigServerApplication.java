package cn.doo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer // 启用config server功能
@EnableEurekaClient
public class ConfigServerApplication {
    //http://localhost:9999/master/config-dev.yml
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}

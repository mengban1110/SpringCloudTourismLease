package all.doo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"cn.doo.repertory.service","cn.doo.email","com.rj.bd.service"})
@MapperScan("all.doo.dao")
public class TourisLeasApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourisLeasApplication.class,args);
    }
}

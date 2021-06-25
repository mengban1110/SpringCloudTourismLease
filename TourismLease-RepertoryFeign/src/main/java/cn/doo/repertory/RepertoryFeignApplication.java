package cn.doo.repertory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 梦伴
 * @desc
 * @time 2021-06-24-2:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.doo.repertory.service"})
@EnableCircuitBreaker
public class RepertoryFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(RepertoryFeignApplication.class, args);
    }
}

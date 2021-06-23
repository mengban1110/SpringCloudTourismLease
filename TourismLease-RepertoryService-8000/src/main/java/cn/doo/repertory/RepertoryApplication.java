package cn.doo.repertory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient//启动eureka的客户端，目的是将当前这个8001的子项目以服务的方式注入到”eureka7001“中
@EnableDiscoveryClient //服务发现
@SpringBootApplication
@EnableCircuitBreaker  //让当前子项目具备服务熔断的功能
public class RepertoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepertoryApplication.class, args);
    }

}

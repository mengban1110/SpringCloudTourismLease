package com.rj.bd.personnel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Desc:
 * @Auther: LuHoYn
 * @Date: 2021-06-24 16:48
 */
// 启动 Eureka 的客户端
@EnableEurekaClient
// 服务发现
@EnableDiscoveryClient
@SpringBootApplication
// 启动服务熔断
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"cn.doo.repertory.service","cn.doo.email"})
public class PersonnelApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelApplication.class, args);

    }

}

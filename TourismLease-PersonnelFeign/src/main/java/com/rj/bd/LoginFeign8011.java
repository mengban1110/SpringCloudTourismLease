package com.rj.bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com.rj.bd.service"})
@EnableCircuitBreaker
public class LoginFeign8011 {

    public static void main(String[] args) {
        SpringApplication.run(LoginFeign8011.class, args);

    }

}

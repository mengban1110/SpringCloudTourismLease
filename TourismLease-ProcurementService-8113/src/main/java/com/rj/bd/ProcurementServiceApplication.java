package com.rj.bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Y
 * @desc
 * @time 2021-06-24-13:20
 */
@SpringBootApplication
@EnableEurekaClient//启动eureka的客户端，目的是将当前这个8002的子项目以服务的方式注入到”eureka7002“中
//@MapperScan(basePackages = "com.rj.bd.user.dao") 如果不想在持久层写 mapper的话，可以在启动类中这样写
@EnableDiscoveryClient //服务发现
@EnableFeignClients(basePackages={"cn.doo.repertory.service"})
public class ProcurementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProcurementServiceApplication.class,args);
    }



}
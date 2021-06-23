package cn.doo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//@ComponentScan(basePackages = {"com.rj.bd.config"})
@SpringBootApplication
@EnableZuulProxy //加上zuul代理注解即可让当前项目启动的时候加载到zuul组件。
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

//    @Bean//将PreIpCheckFilter转变为bean注入到Spring的容器中
//    public PreCheckFilter PreIpCheckFilter(){
//        return new PreCheckFilter();
//    }

}

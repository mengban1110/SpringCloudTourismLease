package com.rj.bd.personnel.utils.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {

    // 配置一个管理后台的 Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername", "admin");

        initParams.put("loginPassword", "123456");

        //默认就是允许所有访问

        initParams.put("allow", "127.0.0.1");

        bean.setInitParameters(initParams);

        return bean;

    }

    // 配置一个 web 监控的 filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();

        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;

    }

}
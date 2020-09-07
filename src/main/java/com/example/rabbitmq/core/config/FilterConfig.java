package com.example.rabbitmq.core.config;

import com.example.rabbitmq.core.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * 添加过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean helloFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("loginFilter");
        registrationBean.setFilter(new LogFilter());
        registrationBean.setOrder(1);
        // 没有配置setUrlPatterns，则是全部拦截
        return registrationBean;
    }
}

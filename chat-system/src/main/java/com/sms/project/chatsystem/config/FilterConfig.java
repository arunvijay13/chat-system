package com.sms.project.chatsystem.config;

import com.sms.project.chatsystem.filter.AuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean() {
        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();

        registrationBean.setFilter(authorizationFilter);
        registrationBean.addUrlPatterns("/login/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

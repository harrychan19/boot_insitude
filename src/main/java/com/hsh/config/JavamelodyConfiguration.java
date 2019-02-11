package com.hsh.config;

import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.SessionListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hushihai
 * @version V1.0, 2019/2/11
 */
@Configuration
@SuppressWarnings("unchecked")
public class JavamelodyConfiguration {

    @Bean
    public FilterRegistrationBean monitorFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new MonitoringFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean sessionListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new SessionListener());
        return servletListenerRegistrationBean;
    }

}
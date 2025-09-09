package com.universitymanagement.configuration;

import com.universitymanagement.interceptors.InterceptorsTimeForSpecificControllerMethod;
import com.universitymanagement.multitenancy.TenantFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigurationForWebController  implements WebMvcConfigurer {

    @Autowired
    private InterceptorsTimeForSpecificControllerMethod interceptorsTimeForSpecificControllerMethod;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorsTimeForSpecificControllerMethod)
                .addPathPatterns("/deleted-course"); // Only apply to this endpoint
    }



}
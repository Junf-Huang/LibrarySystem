package com.homework.one.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("sign-in");
        registry.addViewController("/sign-in").setViewName("sign-in");
        registry.addViewController("/sign-up").setViewName("sign-up");
        registry.addViewController("/403").setViewName("403");
    }
}


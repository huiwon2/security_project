package com.example.security_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String HOME_FORM = "/home";
    private static final String KEY_FORM = "/key";
    private static final String ENVELOPE_FORM = "/envelope";
    private static final String VALIDATE_FORM = "/validate";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName(HOME_FORM);
        registry.addViewController("/key").setViewName(KEY_FORM);
        registry.addViewController("/envelope").setViewName(ENVELOPE_FORM);
        registry.addViewController("/validate").setViewName(VALIDATE_FORM);
    }

}

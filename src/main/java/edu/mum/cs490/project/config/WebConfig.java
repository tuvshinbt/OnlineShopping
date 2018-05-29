package edu.mum.cs490.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by Erdenebayar on 5/4/2018
 */
@Configuration
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "file:///" + env.getProperty("resource.dir"));
        registry.addResourceHandler("/static/**").addResourceLocations(env.getProperty("static.path"));
    }
}
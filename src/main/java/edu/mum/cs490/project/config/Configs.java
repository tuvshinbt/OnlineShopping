package edu.mum.cs490.project.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Erdenebayar on 4/20/2018
 */
@Configuration
@Import({
        WebSecurityConfig.class,
        WebConfig.class,
        MockConnectionConfig.class
})
@ComponentScan({
        "edu.mum.cs490.project.repository",
        "edu.mum.cs490.project.controller",
        "edu.mum.cs490.project.service",
        "edu.mum.cs490.project.utils",
})
public class Configs {
}

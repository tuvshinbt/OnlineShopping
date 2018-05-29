/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.mock.transaction.api.bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author tuvshuu
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //implements WebMvcConfigurer {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig.configure()");
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/").permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/mock**").access("hasRole('ROLE_OSS')")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    /**
     * IN MEMORY authentication application.properties
     *
     * @Bean
     * @Override public UserDetailsService userDetailsService() {
     * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
     * manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
     * return manager; }
     */
}

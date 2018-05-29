package edu.mum.cs490.project.config;

import edu.mum.cs490.project.service.UserService;
import edu.mum.cs490.project.utils.AESConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable()
               /* .headers().defaultsDisabled()
                    .xssProtection()
                    .and()
                    .contentTypeOptions()
                    .and()
                    .httpStrictTransportSecurity()
                    .and()
                .and()*/
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successForwardUrl("/")
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                    .antMatchers("/**/signup").anonymous()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/report/**").hasAnyRole("ADMIN,VENDOR")
                    .antMatchers("/vendor/**").hasRole("VENDOR")
                    .antMatchers("/profile/vendor/**").hasRole("VENDOR")
                    .antMatchers("/profile/card/**").hasAnyRole("VENDOR,CUSTOMER")
                    .antMatchers("/profile/edit/**").hasAnyRole("VENDOR,CUSTOMER,ADMIN")
                    .antMatchers("/profile/**").hasAnyRole("CUSTOMER,ADMIN")
                    .antMatchers("/order/checkout/**", "/order/customer/**").hasRole("CUSTOMER");
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler(){
        return new ForwardAuthenticationFailureHandler("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}

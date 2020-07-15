package com.prinpedia.backend.config;

import com.prinpedia.backend.security.AuthEntryPoint;
import com.prinpedia.backend.security.AuthFailureHandler;
import com.prinpedia.backend.security.AuthSuccessHandler;
import com.prinpedia.backend.serviceImpl.UserSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserSecurityServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    AuthEntryPoint authenticationEntryPoint;

    @Autowired
    AuthSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/entry").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/rank").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/login").permitAll()
                .and().formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
//        super.configure(http);
    }
}

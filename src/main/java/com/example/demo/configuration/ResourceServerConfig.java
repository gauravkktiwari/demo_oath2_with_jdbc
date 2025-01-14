package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                //.antMatchers("/login?username=test4").permitAll() // Permit access without authentication
                .antMatchers("/api/**").authenticated() // Require authentication for /api/**
                .anyRequest().permitAll();
    }
}

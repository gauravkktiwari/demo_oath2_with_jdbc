package com.example.demo.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.example.demo.security.CustomClientDetailsService;

@Configuration
public class ClientDetailsInitializationConfig {

	@Autowired
    private CustomClientDetailsService customClientDetailsService;

    @PostConstruct
    public void init() {
        // Insert client details programmatically
        customClientDetailsService.createOrUpdateClientDetails("mobapp", "mobappsecret", "authorization_code,password,refresh_token",
        		"read,write",null,30,60);
    }
}

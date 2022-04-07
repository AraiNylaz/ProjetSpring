package com.example.jeux_frontend.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.example.user")
public class CustomProperties {

    private String JWTSecret;

    public String getJWTSecret(){
        return JWTSecret;
    }

    public void setJWTSecret(String jwtSecret){
        this.JWTSecret=jwtSecret;
    }
}

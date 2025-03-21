package com.pragma.powerup.infrastructure.feign;

import feign.Client;
import feign.Logger;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient();
    }

    @Bean
    public Logger .Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
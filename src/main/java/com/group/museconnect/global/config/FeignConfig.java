package com.group.museconnect.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.group.museconnect.feign")
@Configuration
public class FeignConfig {
}

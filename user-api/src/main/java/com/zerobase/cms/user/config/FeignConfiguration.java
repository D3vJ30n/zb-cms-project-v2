package com.zerobase.cms.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.zerobase.cms.user.client")
public class FeignConfiguration {
} 
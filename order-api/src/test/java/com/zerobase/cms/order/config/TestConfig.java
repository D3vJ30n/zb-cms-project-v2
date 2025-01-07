package com.zerobase.cms.order.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@ComponentScan(basePackages = "com.zerobase.cms.order")
@EnableJpaRepositories(basePackages = "com.zerobase.cms.order.domain.repository")
@EntityScan(basePackages = "com.zerobase.cms.order.domain.model")
public class TestConfig {
} 
package com.zerobase.cms.order.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
@Import({TestJwtConfig.class, EmbeddedRedisConfig.class})
public class TestConfig {
} 
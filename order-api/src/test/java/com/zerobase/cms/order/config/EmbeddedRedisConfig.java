package com.zerobase.cms.order.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.Socket;

@TestConfiguration
@Profile("test")
public class EmbeddedRedisConfig {
    private RedisServer redisServer;
    private final int port = 6379;

    @PostConstruct
    public void startRedis() {
        if (isRedisRunning()) {
            return;
        }

        try {
            redisServer = new RedisServer(port);
            redisServer.start();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Address already in use")) {
                return;
            }
            throw new RuntimeException("Redis server start failed", e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }

    private boolean isRedisRunning() {
        try (Socket socket = new Socket("localhost", port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
} 
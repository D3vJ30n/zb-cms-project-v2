package com.zerobase.cms.order.client;

public interface RedisClient {
    void put(Long key, Object obj);
    <T> T get(Long key, Class<T> classType);
}

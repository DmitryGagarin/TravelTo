package com.travel.to.travel_to.cache;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailTokenCacheUtil extends SimpleCacheUtilBase<String> {

    private final HashOperations<String, String, String> hashOperations;

    @Autowired
    public EmailTokenCacheUtil(
        @Qualifier("tokenTemplate")
        RedisTemplate<String, String> redisTemplate
    ) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public String save(
        @NotNull String token,
        @NotNull String email,
        @NotNull String key
    ) {
        hashOperations.put(key, email, token);
        return token;
    }

    @Override
    public String findByIdentified(
        @NotNull String email,
        @NotNull String key
    ) {
        return hashOperations.get(key, email);
    }

    @Override
    public String deleteByIdentified(String email) {
        return String.valueOf(hashOperations.delete(email));
    }
}

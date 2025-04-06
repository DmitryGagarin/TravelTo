package com.travel.to.travel_to.cache;

import com.travel.to.travel_to.constants.CacheKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ValidationTokenCacheUtil extends SimpleCacheUtilBase<String> {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;

    @Autowired
    public ValidationTokenCacheUtil(
        @Qualifier("tokenTemplate")
        RedisTemplate<String, String> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public String save(String token, String email) {
        hashOperations.put(CacheKeys.VERIFICATION_TOKEN, email, token);
        return token;
    }

    @Override
    public String findByEmail(String email) {
        return hashOperations.get(CacheKeys.VERIFICATION_TOKEN, email);
    }

    @Override
    public String deleteByEmail(String email) {
        return String.valueOf(hashOperations.delete(email));
    }
}

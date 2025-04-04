package com.travel.to.travel_to.service.cache;

import com.travel.to.travel_to.constants.CacheKeys;
import com.travel.to.travel_to.entity.attraction.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttractionCacheUtil extends CacheUtilBase<Attraction> {

    private final RedisTemplate<String, Attraction> redisTemplate;
    private final HashOperations<String, String, Attraction> hashOperations;

    @Autowired
    public AttractionCacheUtil(
        @Qualifier("attractionTemplate")
        RedisTemplate<String, Attraction> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Attraction save(Attraction attraction) {
        hashOperations.put(CacheKeys.ATTRACTIONS, String.valueOf(attraction.getId()), attraction);
        return attraction;
    }

    @Override
    public List<Attraction> saveAll(List<Attraction> attractions) {
        attractions.forEach(attraction ->
            hashOperations.put(CacheKeys.ATTRACTIONS, String.valueOf(attraction.getId()), attraction)
        );
        return attractions;
    }

    @Override
    public List<Attraction> findAll() {
        return hashOperations.values(CacheKeys.ATTRACTIONS);
    }

    @Override
    public Attraction findById(long id) {
        return (Attraction) redisTemplate.opsForHash().get(CacheKeys.ATTRACTIONS, String.valueOf(id));
    }

}

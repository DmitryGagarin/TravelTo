package com.travel.to.travel_to.cache;

import com.travel.to.travel_to.constants.CacheKeys;
import com.travel.to.travel_to.entity.attraction.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class AttractionCacheUtil extends CacheUtilBase<Attraction> {

    private final HashOperations<String, String, Attraction> hashOperations;

    @Autowired
    public AttractionCacheUtil(
        @Qualifier("attractionTemplate")
        RedisTemplate<String, Attraction> redisTemplate
    ) {
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
        List<Attraction> attractions = hashOperations.values(CacheKeys.ATTRACTIONS);
        attractions.sort(
            Comparator.comparing(Attraction::getPriority).reversed()
        );
        return attractions;
    }

    @Override
    public Attraction findById(long id) {
        return hashOperations.get(CacheKeys.ATTRACTIONS, String.valueOf(id));
    }

    @Override
    public Attraction updateById(long id, Attraction attraction) {
        deleteById(id);
        return save(attraction);
    }

    @Override
    public void deleteById(long id) {
        hashOperations.delete(CacheKeys.ATTRACTIONS, String.valueOf(id));
    }

}

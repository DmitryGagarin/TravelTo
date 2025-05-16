package com.travel.to.travel_to.cache;

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
    public Attraction save(Attraction attraction, String key) {
        hashOperations.put(key, String.valueOf(attraction.getId()), attraction);
        return attraction;
    }

    @Override
    public List<Attraction> saveAll(List<Attraction> attractions, String key) {
        attractions.forEach(attraction ->
            hashOperations.put(key, String.valueOf(attraction.getId()), attraction)
        );
        return attractions;
    }

    @Override
    public List<Attraction> findAll(String key) {
        List<Attraction> attractions = hashOperations.values(key);
        attractions.sort(
            Comparator.comparing(Attraction::getPriority, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
        );
        return attractions;
    }

    @Override
    public Attraction findById(long id, String key) {
        return hashOperations.get(key, String.valueOf(id));
    }

    @Override
    public Attraction updateById(long id, Attraction attraction, String key) {
        deleteById(id, key);
        return save(attraction, key);
    }

    @Override
    public void deleteById(long id, String key) {
        hashOperations.delete(key, String.valueOf(id));
    }

}

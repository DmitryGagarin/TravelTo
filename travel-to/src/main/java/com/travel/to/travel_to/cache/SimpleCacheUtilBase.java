package com.travel.to.travel_to.cache;

public abstract class SimpleCacheUtilBase<T> {

    public abstract T save(T obj, String identifier, String key);

    public abstract T findByIdentified(String identifier, String keys);

    public abstract T deleteByIdentified(String identifier);

}

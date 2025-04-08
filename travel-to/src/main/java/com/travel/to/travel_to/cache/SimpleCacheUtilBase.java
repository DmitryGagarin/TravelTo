package com.travel.to.travel_to.cache;

public abstract class SimpleCacheUtilBase<T> {

    public abstract T save(T obj, String identifier);

    public abstract T findByIdentified(String identifier);

    public abstract T deleteByIdentified(String identifier);

}

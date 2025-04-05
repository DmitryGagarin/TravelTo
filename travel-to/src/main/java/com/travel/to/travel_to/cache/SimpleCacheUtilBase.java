package com.travel.to.travel_to.cache;

public abstract class SimpleCacheUtilBase<T> {

    public abstract T save(T obj, String email);

    public abstract T findByEmail(String email);

    public abstract T deleteByEmail(String email);

}

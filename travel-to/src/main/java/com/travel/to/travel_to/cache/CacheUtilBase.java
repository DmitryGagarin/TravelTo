package com.travel.to.travel_to.cache;

import java.util.List;

public abstract class CacheUtilBase<T> {
    public abstract T save(T obj, String key);

    public abstract List<T> saveAll(List<T> list, String key);

    public abstract List<T> findAll(String key);

    public abstract T findById(long id, String key);

    public abstract T updateById(long id, T object, String key);

    public abstract void deleteById(long id, String key);
}

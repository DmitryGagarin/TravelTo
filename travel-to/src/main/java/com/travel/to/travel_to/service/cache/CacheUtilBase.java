package com.travel.to.travel_to.service.cache;

import java.util.List;

public abstract class CacheUtilBase<T> {
    public abstract T save(T obj);
    public abstract List<T> saveAll(List<T> list);
    public abstract List<T> findAll();
    public abstract T findById(long id);
}

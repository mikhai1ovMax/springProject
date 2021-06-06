package com.max.springproject.services;

import java.util.List;

public interface GenericService<T> {
    T getById(long id);
    public List<T> getAll();
    public T save(T object);
    public void deleteById(long id);
}

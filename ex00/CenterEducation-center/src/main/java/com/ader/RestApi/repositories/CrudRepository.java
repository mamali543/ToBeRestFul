package com.ader.RestApi.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);
    List<T> findAll(int page, int size);
    T save(T entity);
    T update(T entity);
    void delete(Long id);
}

package com.epam.esm.sokolov.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenericService<T> {

    Long create(T entity);

    void delete(T entity);

    void update(T entity);

    T findById(long id);

    List<T> findAll();
}

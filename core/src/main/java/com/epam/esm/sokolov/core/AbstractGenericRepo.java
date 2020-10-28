package com.epam.esm.sokolov.core;

import com.epam.esm.sokolov.repository.RepositoryException;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Repository
public abstract class AbstractGenericRepo<T extends IdentifiedRow> implements GenericRepo<T> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AbstractGenericRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(T entity, String createQuery, String updateQuery) {
        if (nonNull(entity.getId())) {
            update(entity, createQuery);
            return entity.getId();
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource paramMap = createAllParamMapWithoutId(entity);
            try {
                jdbcTemplate.update(createQuery, paramMap, keyHolder);
                return Optional.ofNullable(keyHolder.getKey())
                        .map(Number::longValue)
                        .orElse(0L);
            } catch (DataAccessException e) {
                throw new RepositoryException(format("Resource have not been created, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void delete(T entity, String query) {
        MapSqlParameterSource paramMap = createIdParamMap(entity.getId());
        try {
            jdbcTemplate.update(query, paramMap);
        } catch (DataAccessException e) {
            throw new RepositoryException(format("Resource have not been deleted, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST);
        }
    }

    public void update(T entity, String query) {
        MapSqlParameterSource paramMap = createAllParamMap(entity);
        try {
            jdbcTemplate.update(query, paramMap);
        } catch (DataAccessException e) {
            throw new RepositoryException(format("Resource have not been updated, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST);
        }
    }

    public T findById(long id, String query) {
        MapSqlParameterSource paramMap = createIdParamMap(id);
        try {
            return jdbcTemplate.queryForObject(
                    query,
                    paramMap,
                    this::mapEntity);
        } catch (DataAccessException e) {
            throw new RepositoryException(format("Requested resource not found (id = %s)", id), HttpStatus.NOT_FOUND);
        }
    }

    public List<T> findAll(String query) {
        List<T> list = jdbcTemplate.query(
                query,
                this::mapEntity
        );
        if (CollectionUtils.isEmpty(list)) {
            throw new RepositoryException("List is empty", HttpStatus.NOT_FOUND);
        } else {
            return list;
        }
    }

}

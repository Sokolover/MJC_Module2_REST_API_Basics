package com.epam.esm.sokolov.core;

import com.epam.esm.sokolov.repository.RepositoryException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Repository
public abstract class AbstractGenericRepository<T extends IdentifiedRow> implements GenericRepository<T> {

    private static final String ID = "id";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AbstractGenericRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(T entity, String createQuery, String updateQuery) {
        if (nonNull(entity.getId())) {
            update(entity, updateQuery);
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
                throw new RepositoryException(format("Resource have not been created, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST, this.getClass());
            }
        }
    }

    public void delete(T entity, String query) {
        MapSqlParameterSource paramMap = createIdParamMap(entity.getId());
        try {
            jdbcTemplate.update(query, paramMap);
        } catch (DataAccessException e) {
            throw new RepositoryException(format("Resource have not been deleted, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST, this.getClass());
        }
    }

    public void update(T entity, String query) {
        MapSqlParameterSource paramMap = createAllParamMap(entity);
        try {
            jdbcTemplate.update(query, paramMap);
        } catch (DataAccessException e) {
            throw new RepositoryException(format("Resource have not been updated, bad request (id = %s)", entity.getId()), HttpStatus.BAD_REQUEST, this.getClass());
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
            String message = format("Requested resource not found (id = %s)", id);
            throw new RepositoryException(message, HttpStatus.NOT_FOUND, this.getClass());
        }
    }

    public List<T> findAll(String query) {
        return jdbcTemplate.query(
                query,
                this::mapEntity
        );
    }

    protected MapSqlParameterSource createIdParamMap(Long id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(ID, id);
        return paramMap;
    }
}

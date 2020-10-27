package com.epam.esm.sokolov.core;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractGenericRepo<T extends IdentifiedRow> implements GenericRepo<T> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AbstractGenericRepo(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(T entity, String createQuery, String updateQuery) {

        if (entity.getId() != null) {
            update(entity, createQuery);
            return entity.getId();
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource paramMap = createAllParamMapWithoutId(entity);
            jdbcTemplate.update(createQuery, paramMap, keyHolder);
            //todo test it with mysql!
            return Optional.ofNullable(keyHolder.getKey())
                    .map(Number::longValue)
                    .orElse(0L);
//            if (keyHolder.getKey() instanceof BigInteger) {
//                Optional<BigInteger> generatedId = Optional
//                        .of((BigInteger) keyHolder.getKey());
//                return generatedId.map(BigInteger::longValue).orElse(0L);
//            } else {
//                Optional<Long> generatedId = Optional
//                        .of((Long) keyHolder.getKey());
//                return generatedId.orElse(0L);
//            }
        }
    }

    public void delete(T entity, String query) {
        MapSqlParameterSource paramMap = createIdParamMap(entity.getId());
        jdbcTemplate.update(query, paramMap);
    }

    public void update(T entity, String query) {
        MapSqlParameterSource paramMap = createAllParamMap(entity);
        jdbcTemplate.update(query, paramMap);
    }

    public T findById(long id, String query) {
        MapSqlParameterSource paramMap = createIdParamMap(id);
        return jdbcTemplate.queryForObject(
                query,
                paramMap,
                this::mapEntity);
    }

    public List<T> findAll(String query) {
        return jdbcTemplate.query(
                query, this::mapEntity
        );
    }

}

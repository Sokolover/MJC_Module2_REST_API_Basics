package com.epam.esm.sokolov.core;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public interface GenericRepository<T extends IdentifiedRow> {

    Long create(T entity, String createQuery, String updateQuery);

    void delete(T entity, String query);

    void update(T entity, String query);

    T findById(long id, String query);

    List<T> findAll(String query);

    MapSqlParameterSource createAllParamMapWithoutId(T entity);

    MapSqlParameterSource createAllParamMap(T entity);

    MapSqlParameterSource createIdParamMap(Long id);

    T mapEntity(ResultSet resultSet, int row) throws SQLException;
}

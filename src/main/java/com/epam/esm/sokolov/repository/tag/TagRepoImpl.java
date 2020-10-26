package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.core.AbstractGenericRepo;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TagRepoImpl extends AbstractGenericRepo<Tag> implements TagRepo {

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String INSERT = "insert into tag (name) values (:name)";
    private static final String SELECT_BY_ID = "select * from tag where id = :id";
    private static final String UPDATE = "update tag set name = :name where id = :id";
    private static final String DELETE = "delete from tag where id = :id";
    private static final String SELECT_ALL = "SELECT * FROM tag";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public TagRepoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Long create(Tag entity) {
        return super.create(entity, INSERT);
    }

    public void delete(Tag entity) {
        super.delete(entity, DELETE);
    }

    public void update(Tag entity) {
        super.update(entity, UPDATE);
    }

    public Tag findById(long id) {
        try {
            return super.findById(id, SELECT_BY_ID);
        } catch (DataAccessException e) {
            return new Tag();
        }
    }

    public List<Tag> findAll() {
        return super.findAll(SELECT_ALL);
    }

    @Override
    public MapSqlParameterSource createAllParamMapWithoutId(Tag tag) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(NAME, tag.getName());
        return paramMap;
    }

    @Override
    public MapSqlParameterSource createAllParamMap(Tag tag) {
        MapSqlParameterSource paramMap = createAllParamMapWithoutId(tag);
        paramMap.addValue(ID, tag.getId());
        return paramMap;
    }

    @Override
    public MapSqlParameterSource createIdParamMap(Long id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(ID, id);
        return paramMap;
    }

    @Override
    public Tag mapEntity(ResultSet resultSet, int row) throws SQLException {
        return new Tag(
                resultSet.getLong(ID),
                resultSet.getString(NAME));
    }
}


//import com.epam.esm.sokolov.model.Tag;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.math.BigInteger;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TagRepoImpl implements TagRepo {
//
//    private static final String ID = "id";
//    private static final String NAME = "name";
//
//    private static final String INSERT_TAG = "insert into tag (name) values (:name)";
//    private static final String SELECT_TAG_BY_ID = "select * from tag where id = :id";
//    private static final String UPDATE_TAG = "update tag set name = :name where id = :id";
//    private static final String DELETE_TAG = "delete from tag where id = :id";
//    private static final String SELECT_ALL_TAGS = "SELECT * FROM tag";
//
//    private NamedParameterJdbcTemplate jdbcTemplate;
//
//    public TagRepoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private Tag mapTag(ResultSet resultSet, int row) throws SQLException {
//        return new Tag(
//                resultSet.getLong(ID),
//                resultSet.getString(NAME));
//    }
//
//    public Long createTag(Tag tag) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        MapSqlParameterSource paramMap = createAllParamMapWithoutId(tag);
//        jdbcTemplate.update(INSERT_TAG, paramMap, keyHolder);
//        Optional<BigInteger> generatedId = Optional
//                .of(((BigInteger) keyHolder.getKeys().get("GENERATED_KEY")));
//        return generatedId.map(BigInteger::longValue).orElse(0L);//todo сделать выброс репо≈ксепшен
//    }
//
//    public void deleteTag(Tag tag) {
//        jdbcTemplate.update(DELETE_TAG, createIdParamMap(tag.getId()));
//    }
//
//    public void updateTag(Tag tag) {
//        MapSqlParameterSource paramMap = createAllParamMap(tag);
//        jdbcTemplate.update(UPDATE_TAG, paramMap);
//    }
//
//    public Tag findById(long id) {
//        MapSqlParameterSource paramMap = createIdParamMap(id);
//        try {
//            return jdbcTemplate.queryForObject(
//                    SELECT_TAG_BY_ID,
//                    paramMap,
//                    this::mapTag);
//        } catch (DataAccessException e) {
//            return new Tag();
//        }
//    }
//
//    public List<Tag> findAll() {
//        return jdbcTemplate.query(
//                SELECT_ALL_TAGS, this::mapTag
//        );
//    }
//
//    private MapSqlParameterSource createAllParamMapWithoutId(Tag tag) {
//        MapSqlParameterSource paramMap = new MapSqlParameterSource();
//        paramMap.addValue(NAME, tag.getName());
//        return paramMap;
//    }
//
//    private MapSqlParameterSource createAllParamMap(Tag tag) {
//        MapSqlParameterSource paramMap = createAllParamMapWithoutId(tag);
//        paramMap.addValue(ID, tag.getId());
//        return paramMap;
//    }
//
//    private MapSqlParameterSource createIdParamMap(Long id) {
//        MapSqlParameterSource paramMap = new MapSqlParameterSource();
//        paramMap.addValue(ID, id);
//        return paramMap;
//    }
//}

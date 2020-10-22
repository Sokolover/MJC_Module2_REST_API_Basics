package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagRepoImpl implements TagRepo {

    private static final String INSERT_TAG = "insert into tag (name) values (:name)";
    private static final String SELECT_TAG_BY_ID = "select * from tag where id = ?";
    private static final String UPDATE_TAG = "update tag set name = ? where id = ?";
    private static final String DELETE_TAG = "delete from tag where id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT * FROM tag";

    private JdbcOperations jdbcOperations;

    public TagRepoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private Tag mapTag(ResultSet resultSet, int row) throws SQLException {
        return new Tag(
                resultSet.getLong("id"),
                resultSet.getString("name"));
    }

    public void createTag(Tag tag) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", tag.getName());
        jdbcOperations.update(INSERT_TAG, paramMap);
    }

    public void deleteTag(Tag tag) {
        jdbcOperations.update(DELETE_TAG, tag.getId());
    }

    public void updateTag(Tag tag) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", tag.getId());
        paramMap.put("name", tag.getName());
        jdbcOperations.update(UPDATE_TAG, paramMap);
    }

    public Tag findById(long id) {
        return jdbcOperations.queryForObject(
                SELECT_TAG_BY_ID,
                this::mapTag,
                id);
    }

    public List<Tag> findAll() {
        return jdbcOperations.query(
                SELECT_ALL_TAGS, this::mapTag
        );
    }
}

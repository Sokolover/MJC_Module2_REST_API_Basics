package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.core.AbstractGenericRepository;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Repository
public class TagRepositoryImpl extends AbstractGenericRepository<Tag> implements TagRepository {

    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String INSERT = "insert into tag (name) values (:name)";
    private static final String SELECT_BY_ID = "select * from tag where id = :id";
    private static final String UPDATE = "update tag set name = :name where id = :id";
    private static final String DELETE = "delete from tag where id = :id";
    private static final String SELECT_ALL = "SELECT * FROM tag";
    private static final String SELECT_BY_NAME = "SELECT * FROM tag where name like :name";
    private static final String SELECT_TAGS_BY_GIFT_CERTIFICATE_ID =
            "select tag.* " +
                    "from tag," +
                    "     tag_has_gift_certificate," +
                    "     gift_certificate " +
                    "where tag.id = tag_has_gift_certificate.tag_id " +
                    "  and gift_certificate.id = tag_has_gift_certificate.gift_certificate_id " +
                    "  and gift_certificate.id = :id;";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findTagsByGiftCertificateId(long id) {
        return jdbcTemplate.query(
                SELECT_TAGS_BY_GIFT_CERTIFICATE_ID,
                createIdParamMap(id),
                this::mapEntity);
    }

    public Tag findByName(Tag entity) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(NAME, entity.getName());
        try {
            Optional<Tag> optionalTag = Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_BY_NAME,
                    paramMap,
                    this::mapEntity));
            return optionalTag.orElse(new Tag());
        } catch (DataAccessException e) {
            return new Tag();
        }
    }

    public Long create(Tag entity) {
        return super.create(entity, INSERT, UPDATE);
    }

    public void delete(Tag entity) {
        super.delete(entity, DELETE);
    }

    public void update(Tag entity) {
        super.update(entity, UPDATE);
    }

    public Tag findById(long id) {
//        try {
            return super.findById(id, SELECT_BY_ID);
//        } catch (DataAccessException e) {
//            return new Tag();
//        }
    }

    public List<Tag> findAll() {
        return super.findAll(SELECT_ALL);
    }

    @Override
    public void updateList(List<Tag> tags) {
        for (Tag tag : tags) {
            this.update(tag);
        }
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
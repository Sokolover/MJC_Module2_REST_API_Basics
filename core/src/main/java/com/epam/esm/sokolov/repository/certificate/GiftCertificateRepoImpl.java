package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.core.AbstractGenericRepo;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class GiftCertificateRepoImpl extends AbstractGenericRepo<GiftCertificate> implements GiftCertificateRepo {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String CREATE_DATE = "createDate";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";
    private static final String DURATION = "duration";
    private static final String TAG_ID = "tag_id";
    private static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";

    private static final String INSERT = "insert into gift_certificate (name, description, price, createDate, lastUpdateDate, duration) " +
            "values (:name, :description, :price, :createDate, :lastUpdateDate, :duration)";
    private static final String SELECT_BY_ID = "select * from gift_certificate where id = :id";
    private static final String UPDATE = "update gift_certificate " +
            "set name = :name, description = :description, price = :price, createDate = :createDate, lastUpdateDate = :lastUpdateDate, duration = :duration " +
            "where id = :id";
    private static final String DELETE = "delete from gift_certificate where id = :id";
    private static final String SELECT_ALL = "SELECT * FROM gift_certificate";
    private static final String INSERT_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO tag_has_gift_certificate (tag_id, gift_certificate_id) VALUES (:tag_id, :gift_certificate_id)";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GiftCertificateRepoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    public GiftCertificate mapEntity(ResultSet resultSet, int row) throws SQLException {
        return new GiftCertificate(
                resultSet.getLong(ID),
                resultSet.getString(NAME),
                resultSet.getString(DESCRIPTION),
                resultSet.getBigDecimal(PRICE),
                resultSet.getObject(CREATE_DATE, LocalDateTime.class),
                resultSet.getObject(LAST_UPDATE_DATE, LocalDateTime.class),
                resultSet.getInt(DURATION)
        );
    }

    public void setGiftCertificateTags(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        for (Tag tag : giftCertificate.getTags()) {
            paramMap.addValue(TAG_ID, tag.getId());
            paramMap.addValue(GIFT_CERTIFICATE_ID, giftCertificate.getId());
            jdbcTemplate.update(INSERT_TAG_TO_GIFT_CERTIFICATE, paramMap);
        }
    }

    public Long create(GiftCertificate giftCertificate) {
        return super.create(giftCertificate, INSERT, UPDATE);
    }

    public MapSqlParameterSource createAllParamMapWithoutId(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(NAME, giftCertificate.getName());
        paramMap.addValue(DESCRIPTION, giftCertificate.getDescription());
        paramMap.addValue(PRICE, giftCertificate.getPrice());
        paramMap.addValue(CREATE_DATE, giftCertificate.getCreateDate());
        paramMap.addValue(LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
        paramMap.addValue(DURATION, giftCertificate.getDuration());
        return paramMap;
    }

    public MapSqlParameterSource createAllParamMap(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = createAllParamMapWithoutId(giftCertificate);
        paramMap.addValue(ID, giftCertificate.getId());
        return paramMap;
    }

    @Override
    public MapSqlParameterSource createIdParamMap(Long id) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(ID, id);
        return paramMap;
    }

    public void delete(GiftCertificate giftCertificate) {
        super.delete(giftCertificate, DELETE);
    }

    public void update(GiftCertificate giftCertificate) {
        super.update(giftCertificate, UPDATE);
    }

    public GiftCertificate findById(long id) {//todo добавить доставание тегов из базы
        try {
            return super.findById(id, SELECT_BY_ID);
        } catch (DataAccessException e) {
            return new GiftCertificate();
        }
    }

    public List<GiftCertificate> findAll() {
        return super.findAll(SELECT_ALL);
    }//todo добавить доставание тегов из базы
}

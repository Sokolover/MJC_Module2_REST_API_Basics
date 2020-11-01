package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.core.AbstractGenericRepository;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryConstants.*;
import static com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryUtils.buildUpdateQuery;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractGenericRepository<GiftCertificate> implements GiftCertificateRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GiftCertificateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate mapEntity(ResultSet resultSet, int row) throws SQLException {
        return new GiftCertificate(
                resultSet.getLong(ID),
                resultSet.getString(NAME),
                resultSet.getString(DESCRIPTION),
                resultSet.getBigDecimal(PRICE),
                resultSet.getObject(CREATE_DATE, LocalDateTime.class),
                resultSet.getString(CREATE_DATE_TIME_ZONE),
                resultSet.getObject(LAST_UPDATE_DATE, LocalDateTime.class),
                resultSet.getString(LAST_UPDATE_DATE_TIMEZONE),
                resultSet.getInt(DURATION)
        );
    }

    @Override
    public void setGiftCertificatesToTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            return;
        }
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        tags.forEach(tag -> {
            paramMap.addValue(TAG_ID, tag.getId());
            paramMap.addValue(GIFT_CERTIFICATE_ID, giftCertificate.getId());
            jdbcTemplate.update(INSERT_TAG_TO_GIFT_CERTIFICATE, paramMap);
        });
    }

    @Override
    public void deleteGiftCertificatesToTags(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(GIFT_CERTIFICATE_ID, giftCertificate.getId());
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATES_TO_TAGS, paramMap);
    }

    @Override
    public List<GiftCertificate> findAllByParams(Map<String, String> paramMap) {
        String resultQuery = GiftCertificateRepositoryUtils.buildFindAllByParamQuery(paramMap);
        return jdbcTemplate.query(
                resultQuery,
                this::mapEntity
        );
    }

    @Override
    public Long create(GiftCertificate giftCertificate) {
        String updateQuery = buildUpdateQuery(giftCertificate);
        return super.create(giftCertificate, INSERT, updateQuery);
    }


    public MapSqlParameterSource createAllParamMap(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = createAllParamMapWithoutId(giftCertificate);
        paramMap.addValue(ID, giftCertificate.getId());
        return paramMap;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        super.delete(giftCertificate, DELETE);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        String updateQuery = buildUpdateQuery(giftCertificate);
        super.update(giftCertificate, updateQuery);
    }

    @Override
    public GiftCertificate findById(long id) {
        return super.findById(id, SELECT_BY_ID);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return super.findAll(SELECT_ALL);
    }

    @Override
    public MapSqlParameterSource createAllParamMapWithoutId(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(NAME, giftCertificate.getName());
        paramMap.addValue(DESCRIPTION, giftCertificate.getDescription());
        paramMap.addValue(PRICE, giftCertificate.getPrice());
        paramMap.addValue(CREATE_DATE, giftCertificate.getCreateDate());
        paramMap.addValue(CREATE_DATE_TIME_ZONE, giftCertificate.getCreateDateTimeZone());
        paramMap.addValue(LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
        paramMap.addValue(LAST_UPDATE_DATE_TIMEZONE, giftCertificate.getLastUpdateDateTimeZone());
        paramMap.addValue(DURATION, giftCertificate.getDuration());
        return paramMap;
    }
}

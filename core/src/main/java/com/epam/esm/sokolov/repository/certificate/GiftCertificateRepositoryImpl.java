package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.core.AbstractGenericRepository;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryConstants.*;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractGenericRepository<GiftCertificate> implements GiftCertificateRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GiftCertificateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
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

    public void setGiftCertificatesToTags(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        for (Tag tag : giftCertificate.getTags()) {
            paramMap.addValue(TAG_ID, tag.getId());
            paramMap.addValue(GIFT_CERTIFICATE_ID, giftCertificate.getId());
            jdbcTemplate.update(INSERT_TAG_TO_GIFT_CERTIFICATE, paramMap);
        }
    }

    @Override
    public void deleteGiftCertificatesToTags(GiftCertificate giftCertificate) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue(GIFT_CERTIFICATE_ID, giftCertificate.getId());
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATES_TO_TAGS, paramMap);
    }

    @Override
    public List<GiftCertificate> findAllByParams(Map<String, String> paramMap) {
        GiftCertificateRepositoryUtils giftCertificateRepositoryUtils = new GiftCertificateRepositoryUtils();
        String resultQuery = giftCertificateRepositoryUtils.buildFindAllByParamQuery(paramMap);
        MapSqlParameterSource mapSqlParameterSource = giftCertificateRepositoryUtils.getMapSqlParameterSource();
        return jdbcTemplate.query(
                resultQuery,
                mapSqlParameterSource,
                this::mapEntity
        );
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

    @Override
    public void delete(GiftCertificate giftCertificate) {
        super.delete(giftCertificate, DELETE);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        super.update(giftCertificate, UPDATE);
    }

    @Override
    public GiftCertificate findById(long id) {
//        try {
        return super.findById(id, SELECT_BY_ID);
//        } catch (DataAccessException e) {
//            return new GiftCertificate();
//        }
    }

    @Override
    public List<GiftCertificate> findAll() {
        return super.findAll(SELECT_ALL);
    }
}

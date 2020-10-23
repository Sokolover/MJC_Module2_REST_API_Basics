package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepoImpl implements GiftCertificateRepo {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String CREATE_DATE = "createDate";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";
    private static final String DURATION = "duration";

    private static final String INSERT_CERTIFICATE = "insert into gift_certificate (name, description, price, createDate, lastUpdateDate, duration) " +
            "values (:name, :description, :price, :createDate, :lastUpdateDate, :duration)";
    private static final String SELECT_CERTIFICATE_BY_ID = "select * from gift_certificate where id = ?";
    private static final String UPDATE_CERTIFICATE = "update gift_certificate " +
            "set name = ?, description = ?, price = ?, createDate = ?, lastUpdateDate = ?, duration = ? " +
            "where id = ?";
    private static final String DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";
    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";

    private JdbcOperations jdbcOperations;

    public GiftCertificateRepoImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    private GiftCertificate mapGiftCertificate(ResultSet resultSet, int row) throws SQLException {
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

    public void createGiftCertificate(GiftCertificate giftCertificate) {
        Map<String, Object> paramMap = createAllParamMap(giftCertificate);
        jdbcOperations.update(INSERT_CERTIFICATE, paramMap);
    }

    private Map<String, Object> createAllParamMap(GiftCertificate giftCertificate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(NAME, giftCertificate.getName());
        paramMap.put(DESCRIPTION, giftCertificate.getDescription());
        paramMap.put(PRICE, giftCertificate.getPrice());
        paramMap.put(CREATE_DATE, giftCertificate.getCreateDate());
        paramMap.put(LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
        paramMap.put(DURATION, giftCertificate.getDuration());
        return paramMap;
    }

    public void deleteGiftCertificate(GiftCertificate giftCertificate) {
        jdbcOperations.update(DELETE_CERTIFICATE, giftCertificate.getId());
    }

    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        Map<String, Object> paramMap = createAllParamMap(giftCertificate);
        jdbcOperations.update(UPDATE_CERTIFICATE, paramMap);
    }

    public GiftCertificate findById(long id) {
        return jdbcOperations.queryForObject(
                SELECT_CERTIFICATE_BY_ID,
                this::mapGiftCertificate,
                id);
    }

    public List<GiftCertificate> findAll() {
        return jdbcOperations.query(
                SELECT_ALL_CERTIFICATES, this::mapGiftCertificate
        );
    }
}

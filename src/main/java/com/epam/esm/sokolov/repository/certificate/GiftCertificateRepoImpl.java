//package com.epam.esm.sokolov.repository.certificate;
//
//import com.epam.esm.sokolov.model.GiftCertificate;
//import com.epam.esm.sokolov.model.Tag;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//public class GiftCertificateRepoImpl {
//
//    private static final String INSERT_CERTIFICATE = "insert into gift_certificate (name, description, price, createDate, lastUpdateDate, duration) " +
//            "values (:name, :description, :price, :createDate, :lastUpdateDate, :duration)";
//    private static final String SELECT_CERTIFICATE_BY_ID = "select * from gift_certificate where id = ?";
//    private static final String UPDATE_CERTIFICATE = "update gift_certificate " +
//            "set name = ?, description = ?, price = ?, createDate = ?, lastUpdateDate = ?, duration = ? " +
//            "where id = ?";
//    private static final String DELETE_CERTIFICATE = "delete from gift_certificate where id = ?";
//    private static final String SELECT_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
//
//    private JdbcOperations jdbcOperations;
//
//    public GiftCertificateRepoImpl(JdbcOperations jdbcOperations) {
//        this.jdbcOperations = jdbcOperations;
//    }
//
//    private Tag mapGiftCertificate(ResultSet resultSet, int row) throws SQLException {
//        return new GiftCertificate(
//                resultSet.getLong("id"),
//                resultSet.getString("name"),
//                resultSet.getString("description"),
//                resultSet.getBigDecimal("price"),
//                resultSet.getObject("createDate"),
//                resultSet.getObject("lastUpdateDate"),
//                resultSet.getInt("duration")
//                );
//    }
//
//    public void createTag(Tag tag) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", tag.getName());
//        jdbcOperations.update(INSERT_CERTIFICATE, paramMap);
//    }
//
//    public void deleteTag(Tag tag) {
//        jdbcOperations.update(DELETE_CERTIFICATE, tag.getId());
//    }
//
//    public void updateTag(Tag tag) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", tag.getId());
//        paramMap.put("name", tag.getName());
//        jdbcOperations.update(UPDATE_CERTIFICATE, paramMap);
//    }
//
//    public Tag findById(long id) {
//        return jdbcOperations.queryForObject(
//                SELECT_CERTIFICATE_BY_ID,
//                this::mapGiftCertificate,
//                id);
//    }
//
//    public List<Tag> findAll() {
//        return jdbcOperations.query(
//                SELECT_ALL_CERTIFICATES, this::mapGiftCertificate
//        );
//    }
//}

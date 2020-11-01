package com.epam.esm.sokolov.repository.certificate;

final class GiftCertificateRepositoryConstants {

    static final String ID = "id";
    static final String NAME = "name";
    static final String DESCRIPTION = "description";
    static final String PRICE = "price";
    static final String CREATE_DATE = "createDate";
    static final String CREATE_DATE_TIME_ZONE = "createDateTimeZone";
    static final String LAST_UPDATE_DATE = "lastUpdateDate";
    static final String LAST_UPDATE_DATE_TIMEZONE = "lastUpdateDateTimeZone";
    static final String DURATION = "duration";
    static final String TAG_ID = "tag_id";
    static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
    static final String INSERT = "insert into gift_certificate " +
            "(name, description, price, createDate, createDateTimeZone, lastUpdateDate, lastUpdateDateTimeZone, duration) " +
            "values (:name, :description, :price, :createDate, :createDateTimeZone, :lastUpdateDate, :lastUpdateDateTimeZone, :duration)";
    static final String SELECT_BY_ID = "select * from gift_certificate where id = :id";
    //    static final String UPDATE = "update gift_certificate " +
//            "set name = :name, description = :description, price = :price, createDate = :createDate, " +
//            "createDateTimeZone = :createDateTimeZone, lastUpdateDate = :lastUpdateDate, " +
//            "lastUpdateDateTimeZone = :lastUpdateDateTimeZone, duration = :duration " +
//            "where id = :id";
    static final String DELETE = "delete from gift_certificate where id = :id";
    static final String DELETE_GIFT_CERTIFICATES_TO_TAGS = "delete from tag_has_gift_certificate where gift_certificate_id = :gift_certificate_id";
    static final String SELECT_ALL = "select * from gift_certificate";
    //    static final String SELECT_ALL = "SELECT * " +
//            "FROM gift_certificate, " +
//            "       tag_has_gift_certificate," +
//            "       tag" +
//            " where gift_certificate.id = tag_has_gift_certificate.gift_certificate_id " +
//            " and tag.id = tag_has_gift_certificate.tag_id;";
    static final String INSERT_TAG_TO_GIFT_CERTIFICATE = "INSERT INTO tag_has_gift_certificate (tag_id, gift_certificate_id) VALUES (:tag_id, :gift_certificate_id)";

    private GiftCertificateRepositoryConstants() {

    }
}

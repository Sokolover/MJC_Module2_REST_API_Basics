package com.epam.esm.sokolov.repository.tag;

final class TagRepositoryConstants {

    static final String ID = "id";
    static final String NAME = "name";
    static final String INSERT = "insert into tag (name) values (:name)";
    static final String SELECT_BY_ID = "select * from tag where id = :id";
    static final String UPDATE = "update tag set name = :name where id = :id";
    static final String DELETE = "delete from tag where id = :id";
    static final String SELECT_ALL = "SELECT * FROM tag";
    static final String SELECT_BY_NAME = "SELECT * FROM tag where name like :name";
    static final String SELECT_TAGS_BY_GIFT_CERTIFICATE_ID =
            "select tag.* " +
                    "from tag," +
                    "     tag_has_gift_certificate," +
                    "     gift_certificate " +
                    "where tag.id = tag_has_gift_certificate.tag_id " +
                    "  and gift_certificate.id = tag_has_gift_certificate.gift_certificate_id " +
                    "  and gift_certificate.id = :id;";

    private TagRepositoryConstants() {

    }
}

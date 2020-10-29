package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

class GiftCertificateRepositoryUtils {

    private static final String PART_OF = "partOf";
    private static final String PART_VALUE = "partValue";
    private static final String SORT_BY = "sortBy";
    private static final String SORT_DIRECTION = "sortDirection";
    private static final String TAG_NAME = "tagName";

    private static final String BASE_QUERY = "select gift_certificate.*\n" +
            "from tag,\n" +
            "     tag_has_gift_certificate,\n" +
            "     gift_certificate\n" +
            "where\n" +
            "   tag.id = tag_has_gift_certificate.tag_id\n" +
            "   and gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n";
    private StringBuilder resultQuery = new StringBuilder(BASE_QUERY);
    private MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

    /**
     * This part of query add a condition to order GiftCertificates from database
     * by some parameter (for example name or date) and in witch order (asc/desc)
     *
     * @param paramMap    Map of parameters from request
     * @param resultQuery Main query to witch will be appended a new part of query
     */
    private static void sortByQueryPart(Map<String, String> paramMap, StringBuilder resultQuery) {
        String sortBy = paramMap.get(SORT_BY);
        String sortDirection = paramMap.get(SORT_DIRECTION);
        if (nonNull(sortBy) && nonNull(sortDirection)) {
            resultQuery.append(
                    format("    order by gift_certificate.%s %s", sortBy, sortDirection)
            );
        }
    }

    /**
     * This part of query add a condition to select GiftCertificates from database
     * by a part of a GiftCertificates field (for example name or description)
     *
     * @param paramMap              Map of parameters from request
     * @param resultQuery           Main query to witch will be appended a new part of query
     * @param mapSqlParameterSource Map with named params for NamedParameterJdbcTemplate operations
     */
    private static void findByPartOfQueryPart(Map<String, String> paramMap, StringBuilder resultQuery, MapSqlParameterSource mapSqlParameterSource) {
        String partOf = paramMap.get(PART_OF);
        String partValue = paramMap.get(PART_VALUE);
        if (nonNull(partOf) && nonNull(partValue)) {
            resultQuery.append(
                    format("    and gift_certificate.%s like :partValue%n", partOf)
            );
            //format("%%%s%%", partValue) <==> "%" + partValue + "%"
            mapSqlParameterSource.addValue(PART_VALUE, format("%%%s%%", partValue));
        }
    }

    /**
     * This part of query add a condition to select GiftCertificates from database
     * by one tag name
     *
     * @param paramMap              Map of parameters from request
     * @param resultQuery           Main query to witch will be appended a new part of query
     * @param mapSqlParameterSource Map with named params for NamedParameterJdbcTemplate operations
     */
    private static void findByTagNameQueryPart(Map<String, String> paramMap, StringBuilder resultQuery, MapSqlParameterSource mapSqlParameterSource) {
        String tagName = paramMap.get(TAG_NAME);
        if (nonNull(tagName)) {
            resultQuery.append("    and tag.name like :tagName\n");
            mapSqlParameterSource.addValue(TAG_NAME, tagName);
        }
    }

    static String buildUpdateQuery(GiftCertificate giftCertificate) {

        StringBuilder resultQuery = new StringBuilder("update gift_certificate set ");
        List<String> queryParts = new ArrayList<>();

        String name = giftCertificate.getName();
        if (nonNull(name)) {
            queryParts.add("name = :name");
        }
        String description = giftCertificate.getDescription();
        if (nonNull(description)) {
            queryParts.add("description = :description");
        }
        BigDecimal price = giftCertificate.getPrice();
        if (nonNull(price)) {
            queryParts.add("price = :price");
        }
        LocalDateTime createDate = giftCertificate.getCreateDate();
        if (nonNull(createDate)) {
            queryParts.add("createDate = :createDate");
        }
        String createDateTimeZone = giftCertificate.getCreateDateTimeZone();
        if (nonNull(createDateTimeZone)) {
            queryParts.add("createDateTimeZone = :createDateTimeZone");
        }
        LocalDateTime lastUpdateDate = giftCertificate.getLastUpdateDate();
        if (nonNull(lastUpdateDate)) {
            queryParts.add("lastUpdateDate = :lastUpdateDate");
        }
        String lastUpdateDateTimeZone = giftCertificate.getLastUpdateDateTimeZone();
        if (nonNull(lastUpdateDateTimeZone)) {
            queryParts.add("lastUpdateDateTimeZone = :lastUpdateDateTimeZone");
        }
        Integer duration = giftCertificate.getDuration();
        if (nonNull(duration)) {
            queryParts.add("duration = :duration");
        }

        for (int i = 0; i < queryParts.size() - 1; i++) {
            resultQuery.append(queryParts.get(i))
                    .append(", ");
        }
        resultQuery.append(queryParts.get(queryParts.size() - 1))
                .append(" where id = :id");
        return resultQuery.toString();
    }

    /**
     * @param paramMap Map of parameters from request
     * @return Generated query based on parameters from request
     */
    String buildFindAllByParamQuery(Map<String, String> paramMap) {
        findByTagNameQueryPart(paramMap, resultQuery, mapSqlParameterSource);
        findByPartOfQueryPart(paramMap, resultQuery, mapSqlParameterSource);
        sortByQueryPart(paramMap, resultQuery);
        resultQuery.append(";");
        return resultQuery.toString();
    }

    MapSqlParameterSource getMapSqlParameterSource() {
        return mapSqlParameterSource;
    }
}

package com.epam.esm.sokolov.repository.certificate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

class GiftCertificateRepositoryUtils {

    private static final String BASE_QUERY = "select gift_certificate.*\n" +
            "from tag,\n" +
            "     tag_has_gift_certificate,\n" +
            "     gift_certificate\n" +
            "where\n" +
            "   tag.id = tag_has_gift_certificate.tag_id\n" +
            "   and gift_certificate.id = tag_has_gift_certificate.gift_certificate_id\n";
    private StringBuilder resultQuery = new StringBuilder(BASE_QUERY);
    private MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

    private static void sortByQueryPart(Map<String, String> paramMap, StringBuilder resultQuery) {
        String sortBy = paramMap.get("sortBy");
        String sortDirection = paramMap.get("sortDirection");
        if (nonNull(sortBy) && nonNull(sortDirection)) {
            resultQuery.append(
                    format("    order by gift_certificate.%s %s", sortBy, sortDirection)
            );
        }
    }

    private static void findByPartOfQueryPart(Map<String, String> paramMap, StringBuilder resultQuery, MapSqlParameterSource mapSqlParameterSource) {
        String partOf = paramMap.get("partOf");
        String partValue = paramMap.get("partValue");
        if (nonNull(partOf) && nonNull(partValue)) {
            resultQuery.append(
                    format("    and gift_certificate.%s like :partValue%n", partOf)
            );
//            mapSqlParameterSource.addValue("partValue", format("\\%%s\\%", partValue));//todo сделать чтобы работало
            mapSqlParameterSource.addValue("partValue", "%" + partValue + "%");
        }
    }

    private static void findByTagNameQueryPart(Map<String, String> paramMap, StringBuilder resultQuery, MapSqlParameterSource mapSqlParameterSource) {
        String tagName = paramMap.get("tagName");
        if (nonNull(tagName)) {
            resultQuery.append("    and tag.name like :tagName\n");
            mapSqlParameterSource.addValue("tagName", tagName);
        }
    }

    String buildFindAllByParamQuery(Map<String, String> paramMap) {
        findByTagNameQueryPart(paramMap, resultQuery, mapSqlParameterSource);
        findByPartOfQueryPart(paramMap, resultQuery, mapSqlParameterSource);
        sortByQueryPart(paramMap, resultQuery);
        resultQuery.append(";");
        return resultQuery.toString();
    }

    public MapSqlParameterSource getMapSqlParameterSource() {
        return mapSqlParameterSource;
    }
}

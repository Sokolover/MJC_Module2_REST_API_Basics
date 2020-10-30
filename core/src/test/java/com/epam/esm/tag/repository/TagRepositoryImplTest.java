package com.epam.esm.tag.repository;

import com.epam.esm.sokolov.config.DatabaseTestConfig;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import com.epam.esm.sokolov.repository.tag.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringJUnitConfig(classes = {DatabaseTestConfig.class})
@ActiveProfiles("test")
class TagRepositoryImplTest {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void shouldSaveToCrossTable() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("netflix3");
        giftCertificate.setDescription("5 any films");
        giftCertificate.setPrice(new BigDecimal(4));
        giftCertificate.setCreateDate(LocalDateTime.parse("2020-10-23T09:37:39"));
        giftCertificate.setCreateDateTimeZone("+03:00");
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2020-10-23T15:37:39"));
        giftCertificate.setLastUpdateDateTimeZone("+03:00");
        giftCertificate.setDuration(10);
        List<Tag> tags = new ArrayList<>();
        Tag films = new Tag("films");
        tags.add(films);
        Tag fun = new Tag("fun");
        tags.add(fun);
        giftCertificate.setTags(tags);
        for (Tag tag : giftCertificate.getTags()) {
            tag.setId(tagRepository.create(tag));
        }

        giftCertificate.setId(giftCertificateRepository.create(giftCertificate));
        giftCertificateRepository.setGiftCertificatesToTags(giftCertificate);

        List crossTable = findAll("select * from tag_has_gift_certificate");
        System.out.println(crossTable);
        Assertions.assertEquals(10, crossTable.size());
    }

    List findAll(String query) {
        return jdbcTemplate.query(
                query, this::mapEntity
        );
    }

    Map<Long, Long> mapEntity(ResultSet resultSet, int row) throws SQLException {
        Map<Long, Long> map = new HashMap<>();
        map.put(resultSet.getLong("tag_id"),
                resultSet.getLong("gift_certificate_id"));
        return map;
    }

    @Test
    void shouldFindAll() {
        List<Tag> list = tagRepository.findAll();
        for (Tag tag : list) {
            System.out.println(tag.getName());
        }
        Assertions.assertEquals(5, list.size());
    }

    @Test
    void shouldFindById() {
        Tag testTag = new Tag(1L, "first");
        Tag tag = tagRepository.findById(1);
        Assertions.assertEquals(testTag, tag);
    }

    @Test
    void shouldCreate() {
        Tag testTag = new Tag("fourth");
        Long newTagId = tagRepository.create(testTag);
        testTag.setId(newTagId);
        Assertions.assertEquals(testTag, tagRepository.findById(newTagId));
    }

    @Test
    void shouldDelete() {
        List<Tag> repoBeforeDelete = tagRepository.findAll();
        System.out.println(repoBeforeDelete);
        Tag testTag = new Tag(3L, "third");
        tagRepository.delete(testTag);
        Assertions.assertEquals(repoBeforeDelete.size() - 1, tagRepository.findAll().size());
    }
}
package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.config.DatabaseTestConfig;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class TagRepoImplTest {

    @Autowired
    private TagRepo tagRepo;
    @Autowired
    private GiftCertificateRepo giftCertificateRepo;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    void shouldSaveToCrossTable() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("netflix3");
        giftCertificate.setDescription("5 any films");
        giftCertificate.setPrice(new BigDecimal(4));
        giftCertificate.setCreateDate(LocalDateTime.parse("2020-10-23T09:37:39"));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2020-10-23T15:37:39"));
        giftCertificate.setDuration(10);
        List<Tag> tags = new ArrayList<>();
        Tag films = new Tag("films");
        tags.add(films);
        Tag fun = new Tag("fun");
        tags.add(fun);
        giftCertificate.setTags(tags);
        for (Tag tag : giftCertificate.getTags()) {
            tag.setId(tagRepo.create(tag));
        }
        giftCertificate.setId(giftCertificateRepo.create(giftCertificate));
        giftCertificateRepo.setGiftCertificatesToTags(giftCertificate);
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
        List<Tag> list = tagRepo.findAll();
        for (Tag tag : list) {
            System.out.println(tag.getName());
        }
        Assertions.assertEquals(5, list.size());
    }

    @Test
    void shouldFindById() {
        Tag testTag = new Tag(1L, "first");
        Tag tag = tagRepo.findById(1);
        Assertions.assertEquals(testTag, tag);
    }

    @Test
    void shouldCreate() {
        Tag testTag = new Tag("fourth");
        Long newTagId = tagRepo.create(testTag);
        testTag.setId(newTagId);
        Assertions.assertEquals(testTag, tagRepo.findById(newTagId));
    }

    @Test
    void shouldDelete() {
        List<Tag> repoBeforeDelete = tagRepo.findAll();
        System.out.println(repoBeforeDelete);
        Tag testTag = new Tag(3L, "third");
        tagRepo.delete(testTag);
        Assertions.assertEquals(repoBeforeDelete.size() - 1, tagRepo.findAll().size());
    }
}
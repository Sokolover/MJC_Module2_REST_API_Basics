package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.config.DatabaseTestConfig;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig(classes = {DatabaseTestConfig.class})
@ActiveProfiles("test")
class GiftCertificateServiceImplTest {

    @Autowired
    private GiftCertificateService giftCertificateService;

    @Test
    void shouldUpdateCertificate1() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("films"));
        tags.add(new Tag("films1"));
        giftCertificate.setTags(tags);

        System.out.println(giftCertificateService.findById(16L));
        giftCertificateService.update(giftCertificate);
        System.out.println(giftCertificateService.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateService.findById(16L).getId());
    }

    @Test
    void shouldUpdateCertificate2() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(13L, "films222"));
        tags.add(new Tag(14L, "films333"));
        tags.add(new Tag("films1"));
        tags.add(new Tag("films3"));
        giftCertificate.setTags(tags);

        System.out.println(giftCertificateService.findById(16L));
        giftCertificateService.update(giftCertificate);
        System.out.println(giftCertificateService.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateService.findById(16L).getId());
    }

    @Test
    void shouldUpdateCertificate3() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(13L, "films222"));
        giftCertificate.setTags(tags);

        System.out.println(giftCertificateService.findById(16L));
        giftCertificateService.update(giftCertificate);
        System.out.println(giftCertificateService.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateService.findById(16L).getId());
    }
}
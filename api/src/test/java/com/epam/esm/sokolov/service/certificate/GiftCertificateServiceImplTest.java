package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.converter.TagConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryImpl;
import com.epam.esm.sokolov.repository.tag.TagRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;
    @Mock
    private GiftCertificateRepositoryImpl giftCertificateRepositoryImpl;
    @Mock
    private TagRepositoryImpl tagRepositoryImpl;
    @Autowired
    private GiftCertificateConverter giftCertificateConverter;

    @Test
    void shouldFindAllByAllParams() {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("partOf", "name");
        paramMap.put("partValue", "flix");
        paramMap.put("sortBy", "lastUpdateDate");
        paramMap.put("sortDirection", "desc");

        List<GiftCertificate> giftCertificatesFromDatabase = createGiftCertificatesFromDatabase();
        Mockito.when(giftCertificateRepositoryImpl.findAllByParams(paramMap))
                .thenReturn(giftCertificatesFromDatabase);
        List<GiftCertificateDTO> giftCertificatesFromService = giftCertificateServiceImpl.findAllByParams(paramMap);

        printResult(giftCertificatesFromService);
    }

    @Test
    void shouldFindAllBySeveralParams1() {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("sortBy", "lastUpdateDate");
        paramMap.put("sortDirection", "desc");

        List<GiftCertificate> giftCertificatesFromDatabase = createGiftCertificatesFromDatabase();
        Mockito.when(giftCertificateRepositoryImpl.findAllByParams(paramMap))
                .thenReturn(giftCertificatesFromDatabase);
        Mockito.when(tagRepositoryImpl.findTagsByGiftCertificateId(15))
                .thenReturn(new ArrayList<>());
        Mockito.when(tagRepositoryImpl.findTagsByGiftCertificateId(16))
                .thenReturn(new ArrayList<>());

        List<GiftCertificateDTO> giftCertificatesFromService = giftCertificateServiceImpl.findAllByParams(paramMap);

        printResult(giftCertificatesFromService);
    }

    @Test
    void shouldFindAllBySeveralParams2() {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("partOf", "name");
        paramMap.put("partValue", "flix");

        List<GiftCertificate> giftCertificatesFromDatabase = createGiftCertificatesFromDatabase();
        Mockito.when(giftCertificateRepositoryImpl.findAllByParams(paramMap))
                .thenReturn(giftCertificatesFromDatabase);
        List<GiftCertificateDTO> giftCertificatesFromService = giftCertificateServiceImpl.findAllByParams(paramMap);

        printResult(giftCertificatesFromService);
    }

    private void printResult(List<GiftCertificateDTO> giftCertificatesFromService) {
        for (GiftCertificateDTO giftCertificate : giftCertificatesFromService) {
            System.out.println(giftCertificate);
        }
    }

    private List<GiftCertificate> createGiftCertificatesFromDatabase() {
        GiftCertificate giftCertificate1 = new GiftCertificate(15L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T09:37:39"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T14:37:39.000"),
                "+03:00",
                10);
        GiftCertificate giftCertificate2 = new GiftCertificate(16L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T09:37:39"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T15:37:39.000"),
                "+03:00",
                10);
        List<GiftCertificate> giftCertificatesFromDatabase = new ArrayList<>();
        giftCertificatesFromDatabase.add(giftCertificate1);
        giftCertificatesFromDatabase.add(giftCertificate2);
        return giftCertificatesFromDatabase;
    }

    @Test
    void shouldUpdateCertificate1() {
        GiftCertificateDTO giftCertificate = new GiftCertificateDTO();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("films"));
        tags.add(new Tag("films1"));
        List<TagDTO> tagDTOS = TagConverter.convertTagsDtosFromTag(tags);
        giftCertificate.setTags(tagDTOS);

        System.out.println(giftCertificateServiceImpl.findById(16L));
        giftCertificateServiceImpl.update(giftCertificate);
        System.out.println(giftCertificateServiceImpl.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateServiceImpl.findById(16L).getId());
    }

    @Test
    void shouldUpdateCertificate2() {
        GiftCertificateDTO giftCertificate = new GiftCertificateDTO();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(13L, "films222"));
        tags.add(new Tag(14L, "films333"));
        tags.add(new Tag("films1"));
        tags.add(new Tag("films3"));
        List<TagDTO> tagDTOS = TagConverter.convertTagsDtosFromTag(tags);
        giftCertificate.setTags(tagDTOS);

        System.out.println(giftCertificateServiceImpl.findById(16L));
        giftCertificateServiceImpl.update(giftCertificate);
        System.out.println(giftCertificateServiceImpl.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateServiceImpl.findById(16L).getId());
    }

    @Test
    void shouldUpdateCertificate3() {
        GiftCertificateDTO giftCertificate = new GiftCertificateDTO();
        giftCertificate.setId(16L);
        giftCertificate.setName("netflix2");
        giftCertificate.setDescription("5 any films1");
        giftCertificate.setPrice(new BigDecimal("5.55"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(13L, "films222"));
        List<TagDTO> tagDTOS = TagConverter.convertTagsDtosFromTag(tags);
        giftCertificate.setTags(tagDTOS);

        System.out.println(giftCertificateServiceImpl.findById(16L));
        giftCertificateServiceImpl.update(giftCertificate);
        System.out.println(giftCertificateServiceImpl.findById(16L));

        Assertions.assertEquals(giftCertificate.getId(), giftCertificateServiceImpl.findById(16L).getId());
    }
}
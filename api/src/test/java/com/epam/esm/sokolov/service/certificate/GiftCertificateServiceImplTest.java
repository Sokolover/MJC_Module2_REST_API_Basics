package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepositoryImpl;
import com.epam.esm.sokolov.repository.tag.TagRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    @Spy
    private GiftCertificateConverter giftCertificateConverter;

    @Test//todo посмотреть бест практис по названию тестов
    void shouldCreate() {
        GiftCertificateDTO giftCertificateToCreate = getGiftCertificateDTOToCreate();
        Mockito.doReturn(1L).when(giftCertificateRepositoryImpl).create(getGiftCertificateToCreate());
        Assertions.assertEquals(1L, giftCertificateServiceImpl.create(giftCertificateToCreate));
    }

    @Test
    void shouldFindAllByAllParams() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("partOf", "name");
        paramMap.put("partValue", "flix");
        paramMap.put("sortBy", "lastUpdateDate");
        paramMap.put("sortDirection", "desc");

        testPatternForFindAllByAllParams(paramMap);
    }

    @Test
    void shouldFindAllBySeveralParams1() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("sortBy", "lastUpdateDate");
        paramMap.put("sortDirection", "desc");

        testPatternForFindAllByAllParams(paramMap);
    }

    @Test
    void shouldFindAllBySeveralParams2() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tagName", "fun");
        paramMap.put("partOf", "name");
        paramMap.put("partValue", "flix");

        testPatternForFindAllByAllParams(paramMap);
    }

    private void testPatternForFindAllByAllParams(Map<String, String> paramMap) {
        List<GiftCertificate> giftCertificatesFromDatabase = createGiftCertificatesFromDatabase();
        Mockito.when(giftCertificateRepositoryImpl.findAllByParams(paramMap))
                .thenReturn(giftCertificatesFromDatabase);

        Mockito.doReturn(new ArrayList<>()).when(tagRepositoryImpl).findTagsByGiftCertificateId(15L);
        Mockito.doReturn(new ArrayList<>()).when(tagRepositoryImpl).findTagsByGiftCertificateId(16L);

        List<GiftCertificateDTO> giftCertificatesFromService = giftCertificateServiceImpl.findAllByParams(paramMap);
        List<GiftCertificateDTO> expected = expectedGiftCertificatesFromService();

        Assertions.assertEquals(expected, giftCertificatesFromService);
    }

    private List<GiftCertificate> createGiftCertificatesFromDatabase() {
        GiftCertificate giftCertificate1 = getGiftCertificate1();
        GiftCertificate giftCertificate2 = getGiftCertificate2();
        List<GiftCertificate> giftCertificatesFromDatabase = new ArrayList<>();
        giftCertificatesFromDatabase.add(giftCertificate1);
        giftCertificatesFromDatabase.add(giftCertificate2);
        return giftCertificatesFromDatabase;
    }

    private GiftCertificateDTO getGiftCertificateDTOToCreate() {
        return new GiftCertificateDTO(
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                ZonedDateTime.parse("2020-10-23T09:37:39+03:00"),
                ZonedDateTime.parse("2020-10-23T14:37:39+03:00"),
                10,
                new ArrayList<>());
    }

    private GiftCertificate getGiftCertificateToCreate() {
        return new GiftCertificate(
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T06:37:39.000"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T11:37:39.000"),
                "+03:00",
                10,
                new ArrayList<>());
    }

    private GiftCertificate getGiftCertificate1() {
        return new GiftCertificate(15L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T06:37:39.000"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T11:37:39.000"),
                "+03:00",
                10);
    }

    private GiftCertificate getGiftCertificate2() {
        return new GiftCertificate(16L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T06:37:39.000"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T12:37:39.000"),
                "+03:00",
                10);
    }

    private List<GiftCertificateDTO> expectedGiftCertificatesFromService() {
        GiftCertificateDTO giftCertificate1 = new GiftCertificateDTO(15L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                ZonedDateTime.parse("2020-10-23T09:37:39+03:00"),
                ZonedDateTime.parse("2020-10-23T14:37:39+03:00"),
                10,
                new ArrayList<>());
        GiftCertificateDTO giftCertificate2 = new GiftCertificateDTO(16L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                ZonedDateTime.parse("2020-10-23T09:37:39+03:00"),
                ZonedDateTime.parse("2020-10-23T15:37:39+03:00"),
                10,
                new ArrayList<>());
        List<GiftCertificateDTO> giftCertificatesFromDatabase = new ArrayList<>();
        giftCertificatesFromDatabase.add(giftCertificate1);
        giftCertificatesFromDatabase.add(giftCertificate2);
        return giftCertificatesFromDatabase;
    }
}
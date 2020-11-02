package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.config.DatabaseTestConfig;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepository;
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
public class GiftCertificateRepositoryImplTest {

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
        giftCertificate.getTags().forEach(tag -> tag.setId(tagRepository.create(tag)));

        giftCertificate.setId(giftCertificateRepository.create(giftCertificate));
        giftCertificateRepository.setGiftCertificatesToTags(giftCertificate);

        List crossTable = findAll();
        Assertions.assertEquals(10, crossTable.size());
    }

    List findAll() {
        return jdbcTemplate.query(
                "select * from tag_has_gift_certificate", this::mapEntity
        );
    }
//todo чтобы проверять test coverage jacoco plaguing

    Map<Long, Long> mapEntity(ResultSet resultSet, int row) throws SQLException {
        Map<Long, Long> map = new HashMap<>();
        map.put(resultSet.getLong("tag_id"),
                resultSet.getLong("gift_certificate_id"));
        return map;
    }

    /*
    todo review of theory on demo

       почитать принципы REST

       посмотреть
        - bean definition,
        - spring application context (это map которая хранит инфу по созданию бинов),
        - factory bean и bean factory (разные вещи)

        Динеш Раджпут Spring все паттерны программирования

        инжекция прототайп бина в синглтон бин
        (прототайп не пересоздаётся)
        решается:
        лукап, провайдер, апп контекст

        репоситорий и сервис аннотации отличаются тем что репозитори может обрабатывать ошибки из jdbc template
        разница в обработке ошибок

        по умолчанию бины называются как типы класса только с маленькой буквы

        треугольник тестирования почитать (почему нельзя пистаь только апи тесты)

        юнит тесты используются для тестирования любого уровня и чтобы проверить маленькую часть кода

        почитать про юнит и интеграционный тесты - TDD (понять зачем юнит тесты) BDD

        optional нужен для стримов а не только для проверок на null. суть - возвращать значения, а не передавать куда-то

        прочитать про параллельные стримы когда их использовать

     */

    @Test
    void shouldFindAll() {
        List<Tag> list = tagRepository.findAll();
        Assertions.assertEquals(5, list.size());
    }

    @Test
    void shouldFindById() {
        GiftCertificate giftCertificateFromDatabase = giftCertificateRepository.findById(15L);
        GiftCertificate giftCertificate = getGiftCertificate();
        Assertions.assertEquals(giftCertificate, giftCertificateFromDatabase);
    }

    @Test
    void shouldDelete() {
        List<GiftCertificate> giftCertificateRepositoryBeforeDelete = giftCertificateRepository.findAll();
        GiftCertificate giftCertificateToDelete = getGiftCertificate();
        giftCertificateRepository.delete(giftCertificateToDelete);
        List<GiftCertificate> giftCertificateRepositoryAfterDelete = giftCertificateRepository.findAll();
        Assertions.assertEquals(giftCertificateRepositoryBeforeDelete.size() - 1, giftCertificateRepositoryAfterDelete.size());
    }

    private GiftCertificate getGiftCertificate() {
        return new GiftCertificate(15L,
                "netflix",
                "5 any films",
                new BigDecimal("5.55"),
                LocalDateTime.parse("2020-10-23T09:37:39.000"),
                "+03:00",
                LocalDateTime.parse("2020-10-23T14:37:39.000"),
                "+03:00",
                10);
    }
}

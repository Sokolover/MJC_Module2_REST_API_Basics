package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.config.SpringJdbcConfig;
import com.epam.esm.sokolov.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(
//        classes = {SpringJdbcConfig.class, WebConfig.class, GiftCertificateWebApplicationInitializer.class},
//        loader = AnnotationConfigContextLoader.class)
//@SpringJUnitConfig(classes = {SpringJdbcConfig.class, DatabaseTestConfig.class})
@SpringJUnitConfig(classes = {SpringJdbcConfig.class})
@WebAppConfiguration
class TagRepoImplTest {

    @Resource
    private TagRepoImpl tagRepoImpl;

//    @Test
//    void shouldCreateTagAndFindItById() {
//
//        Tag tagToWrite = new Tag("new tag (4-th)");
//        tagRepoImpl.createTag(tagToWrite);
//        Tag tagRead = tagRepoImpl.findById(4L);
//        Assertions.assertEquals(tagToWrite, tagRead);
//    }

    @Test
    void shouldFindAll() {
        List<Tag> list = tagRepoImpl.findAll();
        for (Tag tag : list) {
            System.out.println(tag.getName());
        }
        Assertions.assertEquals(3, list.size());
    }

}
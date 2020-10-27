package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.config.SpringJdbcConfig;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepo;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(
//        classes = {SpringJdbcConfig.class, WebConfig.class, GiftCertificateWebApplicationInitializer.class},
//        loader = AnnotationConfigContextLoader.class)
//@SpringJUnitConfig(classes = {SpringJdbcConfig.class, DatabaseTestConfig.class})
@SpringJUnitConfig(classes = {SpringJdbcConfig.class})
@WebAppConfiguration
class TagRepoImplTest {

    @Resource
    private TagRepo tagRepo;
//    private AnnotationConfigApplicationContext context;

    //    @Test
//    void shouldCreateTagAndFindItById() {
//
//        Tag tagToWrite = new Tag("new tag (4-th)");
//        tagRepoImpl.createTag(tagToWrite);
//        Tag tagRead = tagRepoImpl.findById(4L);
//        Assertions.assertEquals(tagToWrite, tagRead);
//    }

//    @Before
//    public void setUp() {
//        context = new AnnotationConfigApplicationContext();
//        context.getEnvironment().setActiveProfiles("dao", "test");
//        context.register(SpringJdbcConfig.class);
//        context.refresh();
//        tagRepoImpl = context.getBean("tagRepoImpl", TagRepoImpl.class);
//    }

    @Test
    void shouldFindAll() {
        List<Tag> list = tagRepo.findAll();
        for (Tag tag : list) {
            System.out.println(tag.getName());
        }
        Assertions.assertEquals(3, list.size());
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
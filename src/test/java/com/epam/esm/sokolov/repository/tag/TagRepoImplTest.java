package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.config.H2TestProfileJdbcConfig;
import com.epam.esm.sokolov.config.SpringJdbcConfig;
import com.epam.esm.sokolov.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;

@ContextConfiguration(
        classes = {H2TestProfileJdbcConfig.class},
        loader = AnnotationConfigContextLoader.class)
class TagRepoImplTest {

    @Resource
    private TagRepoImpl tagRepoImpl;

    @Test
    void Test() {

        Tag tagRepo = new Tag(2L, "2-nd");
        tagRepoImpl.createTag(tagRepo);

        Tag tag = tagRepoImpl.findById(2L);
        System.out.println(tag);
    }

}
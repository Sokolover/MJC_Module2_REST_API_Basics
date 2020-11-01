package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.config.DatabaseTestConfig;
import com.epam.esm.sokolov.model.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig(classes = {DatabaseTestConfig.class})
@ActiveProfiles("test")
class TagRepositoryImplTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void shouldFindAll() {
        List<Tag> tagsFromDatabase = tagRepository.findAll();
        Assertions.assertEquals(5, tagsFromDatabase.size());
    }

    @Test
    void shouldFindById() {
        Tag testTag = new Tag(1L, "first");
        Tag tag = tagRepository.findById(1);
        Assertions.assertEquals(testTag, tag);
    }

    @Test
    void shouldFindByName() {
        Tag testTag = new Tag(1L, "first");
        Tag tag = tagRepository.findByName(testTag);
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
        Tag testTag = new Tag(3L, "third");
        tagRepository.delete(testTag);
        Assertions.assertEquals(repoBeforeDelete.size() - 1, tagRepository.findAll().size());
    }
}
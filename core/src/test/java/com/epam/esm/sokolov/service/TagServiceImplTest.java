package com.epam.esm.sokolov.service;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepositoryImpl;
import com.epam.esm.sokolov.service.tag.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagServiceImpl;
    @Mock
    TagRepositoryImpl tagRepoImpl;

    @Test
    public void shouldSaveAndReadSameValue() {

        Tag tagRepo = new Tag(1L, "1-st");
        Mockito.when(tagRepoImpl.findById(1L)).thenReturn(tagRepo);

        Tag tagService = tagServiceImpl.findById(1L);
        Assertions.assertEquals(tagRepo, tagService);
    }
}
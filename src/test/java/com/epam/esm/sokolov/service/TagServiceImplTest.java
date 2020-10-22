package com.epam.esm.sokolov.service;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepoImpl;
import com.epam.esm.sokolov.service.tag.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagServiceImpl;
    @Mock
    TagRepoImpl tagRepoImpl;

    @Test
    void shouldSaveAndReadSameValue() {

        Tag tagRepo = new Tag(1L, "1-st");
        Mockito.when(tagRepoImpl.findById(1L)).thenReturn(tagRepo);

        Tag tagService = tagServiceImpl.findById(1L);
        assertEquals(tagRepo, tagService);
    }

}
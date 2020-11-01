package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.model.Tag;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class TagServiceImplTest {

    @InjectMocks
    TagServiceImpl tagServiceImpl;
    @Mock
    TagRepositoryImpl tagRepositoryImpl;

    @Test
    void shouldFindById() {
        Tag tagFromRepository = new Tag(1L, "first");
        Mockito.when(tagRepositoryImpl.findById(1L)).thenReturn(tagFromRepository);
        Tag tagFromService = tagServiceImpl.findById(1L);
        Assertions.assertEquals(tagFromRepository, tagFromService);
    }

    @Test
    void shouldFindAll() {
        List<Tag> tagsFromRepository = new ArrayList<>();
        tagsFromRepository.add(new Tag(1L, "first"));
        tagsFromRepository.add(new Tag(2L, "second"));
        tagsFromRepository.add(new Tag(3L, "third"));
        Mockito.when(tagRepositoryImpl.findAll()).thenReturn(tagsFromRepository);
        List<Tag> tagsFromService = tagServiceImpl.findAll();
        Assertions.assertEquals(tagsFromRepository, tagsFromService);
    }

    @Test
    void shouldCreate(){
        Tag newTag = new Tag(1L, "first");
        Mockito.when(tagRepositoryImpl.create(newTag)).thenReturn(newTag.getId());
        Long tagIdFromServiceCreate = tagServiceImpl.create(newTag);
        Assertions.assertEquals(newTag.getId(), tagIdFromServiceCreate);
    }
}
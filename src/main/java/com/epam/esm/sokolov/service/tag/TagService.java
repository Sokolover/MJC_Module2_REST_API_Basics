package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {

    Long createTag(Tag tag);

    void deleteTag(Tag tag);

    void updateTag(Tag tag);

    Tag findById(long id);

    List<Tag> findAll();
}

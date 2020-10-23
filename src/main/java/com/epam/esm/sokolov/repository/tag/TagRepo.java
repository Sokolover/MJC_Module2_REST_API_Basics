package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo {

    Long createTag(Tag tag);

    void deleteTag(Tag tag);

    void updateTag(Tag tag);

    Tag findById(long id);

    List<Tag> findAll();
}

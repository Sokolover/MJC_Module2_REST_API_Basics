package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.core.GenericService;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService extends GenericService<Tag> {

    void updateList(List<Tag> tags);

    List<Long> createList(List<Tag> tags);
}

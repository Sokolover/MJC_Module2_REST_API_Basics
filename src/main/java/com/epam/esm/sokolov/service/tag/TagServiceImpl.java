package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Long createTag(Tag tag) {
        return tagRepo.createTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        tagRepo.deleteTag(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tagRepo.updateTag(tag);
    }

    @Override
    public Tag findById(long id) {
        return tagRepo.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }
}

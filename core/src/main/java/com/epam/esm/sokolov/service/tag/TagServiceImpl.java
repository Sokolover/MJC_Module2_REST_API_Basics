package com.epam.esm.sokolov.service.tag;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.tag.TagRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepo tagRepo;

    public TagServiceImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Long create(Tag tag) {
        return tagRepo.create(tag);
    }

    @Override
    public void delete(Tag tag) {
        tagRepo.delete(tag);
    }

    @Override
    public void update(Tag tag) {
        tagRepo.update(tag);
    }

    @Override
    public Tag findById(long id) {
        return tagRepo.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepo.findAll();
    }

    @Override
    public void updateList(List<Tag> tags) {
        for (Tag tag : tags) {
            this.update(tag);
        }
    }

    @Override
    public List<Long> createList(List<Tag> tags) {
        List<Long> newIdList = new ArrayList<>();
        for (Tag tag : tags) {
            newIdList.add(this.create(tag));
        }
        return newIdList;
    }
}

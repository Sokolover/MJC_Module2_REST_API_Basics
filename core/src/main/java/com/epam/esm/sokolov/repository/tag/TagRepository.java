package com.epam.esm.sokolov.repository.tag;

import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository {

    Long create(Tag entity);

    void delete(Tag entity);

    void update(Tag entity);

    Tag findById(long id);

    List<Tag> findAll();

    Tag findByName(Tag entity);

    List<Tag> findTagsByGiftCertificateId(long id);

    void updateList(List<Tag> tags);
}

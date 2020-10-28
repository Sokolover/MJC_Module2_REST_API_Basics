package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import com.epam.esm.sokolov.repository.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Long create(GiftCertificate giftCertificate) {
        createTags(giftCertificate);
        Long newId = giftCertificateRepository.create(giftCertificate);
        giftCertificate.setId(newId);
        giftCertificateRepository.setGiftCertificatesToTags(giftCertificate);
        return newId;
    }

    private void createTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (CollectionUtils.isEmpty(tags)) {
            return;
        }
        for (Tag tag : tags) {
            Tag tagFromDb = tagRepository.findByName(tag);
            if (isNull(tagFromDb.getId())) {
                createNewTag(tag);
            } else {
                tag.setId(tagFromDb.getId());
            }
        }
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        giftCertificateRepository.deleteGiftCertificatesToTags(giftCertificate);
        giftCertificateRepository.delete(giftCertificate);
    }

    @Override
    @Transactional
    public void update(GiftCertificate giftCertificate) {

        if (giftCertificate.getId() == null) {
            return;
        }
        setNewFieldValues(giftCertificate);

        tagRepository.updateList(giftCertificate.getTags());
        giftCertificateRepository.deleteGiftCertificatesToTags(giftCertificate);
        giftCertificateRepository.setGiftCertificatesToTags(giftCertificate);
        giftCertificateRepository.update(giftCertificate);
    }

    private void setNewFieldValues(GiftCertificate giftCertificate) {
        GiftCertificate giftCertificateFromDatabase = findById(giftCertificate.getId());
        setName(giftCertificate, giftCertificateFromDatabase);
        setDescription(giftCertificate, giftCertificateFromDatabase);
        setPrice(giftCertificate, giftCertificateFromDatabase);
        setCreateDate(giftCertificate, giftCertificateFromDatabase);
        setLastUpdateDate(giftCertificate, giftCertificateFromDatabase);
        setDuration(giftCertificate, giftCertificateFromDatabase);
        setTags(giftCertificate, giftCertificateFromDatabase);
    }

    private void setTags(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        List<Tag> tags = giftCertificate.getTags();
        if (isNull(tags)) {
            List<Tag> tagsGiftCertificateFromDatabase = giftCertificateFromDatabase.getTags();
            giftCertificate.setTags(tagsGiftCertificateFromDatabase);
        } else {
            updateTags(tags);
        }
    }

    private void setDuration(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        Integer duration = giftCertificate.getDuration();
        if (isNull(duration)) {
            giftCertificate.setDuration(giftCertificateFromDatabase.getDuration());
        }
    }

    private void setLastUpdateDate(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        LocalDateTime lastUpdateDate = giftCertificate.getLastUpdateDate();
        if (isNull(lastUpdateDate)) {
            giftCertificate.setLastUpdateDate(giftCertificateFromDatabase.getLastUpdateDate());
        }
    }

    private void setCreateDate(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        LocalDateTime createDate = giftCertificate.getCreateDate();
        if (isNull(createDate)) {
            giftCertificate.setCreateDate(giftCertificateFromDatabase.getCreateDate());
        }
    }

    private void setPrice(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        BigDecimal price = giftCertificate.getPrice();
        if (isNull(price)) {
            giftCertificate.setPrice(giftCertificateFromDatabase.getPrice());
        }
    }

    private void setDescription(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        String description = giftCertificate.getDescription();
        if (isNull(description)) {
            giftCertificate.setDescription(giftCertificateFromDatabase.getDescription());
        }
    }

    private void setName(GiftCertificate giftCertificate, GiftCertificate giftCertificateFromDatabase) {
        String name = giftCertificate.getName();
        if (isNull(name)) {
            giftCertificate.setName(giftCertificateFromDatabase.getName());
        }
    }

    private void updateTags(List<Tag> tags) {
        for (Tag tag : tags) {
            Tag findByNameTag = tagRepository.findByName(tag);
            if (isNull(findByNameTag.getId())) {
                createNewTag(tag);
            } else {
                updateTag(tag, findByNameTag);
            }
        }
    }

    private void updateTag(Tag tag, Tag findByNameTag) {
        if (findByNameTag.getId().equals(tag.getId())) {
            tagRepository.update(tag);
        } else {
            tag.setId(findByNameTag.getId());
        }
    }

    private void createNewTag(Tag tag) {
        Long newId = tagRepository.create(tag);
        tag.setId(newId);
    }

    @Override
    @Transactional
    public GiftCertificate findById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        List<Tag> tags = tagRepository.findTagsByGiftCertificateId(id);
        giftCertificate.setTags(tags);
        return giftCertificate;
    }

    @Override
    @Transactional
    public List<GiftCertificate> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        setTagsToGiftCertificates(giftCertificates);
        return giftCertificates;
    }

    private void setTagsToGiftCertificates(List<GiftCertificate> giftCertificates) {
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = tagRepository.findTagsByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTags(tags);
        }
    }

    @Override
    public List<GiftCertificate> findAllByParams(Map<String, String> paramMap) {
        List<GiftCertificate> giftCertificates;
        if (CollectionUtils.isEmpty(paramMap)) {
            giftCertificates = giftCertificateRepository.findAll();
        } else {
            giftCertificates = giftCertificateRepository.findAllByParams(paramMap);
        }
        setTagsToGiftCertificates(giftCertificates);
        return giftCertificates;
    }
}

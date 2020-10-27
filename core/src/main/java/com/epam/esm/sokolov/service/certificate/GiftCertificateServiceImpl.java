package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepo;
import com.epam.esm.sokolov.repository.tag.TagRepo;
import com.epam.esm.sokolov.service.tag.TagService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private TagService tagService;
    private GiftCertificateRepo giftCertificateRepo;
    private TagRepo tagRepo;

    public GiftCertificateServiceImpl(TagService tagService, GiftCertificateRepo giftCertificateRepo, TagRepo tagRepo) {
        this.tagService = tagService;
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    public Long create(GiftCertificate giftCertificate) {
        createTags(giftCertificate);
        Long newId = giftCertificateRepo.create(giftCertificate);
        giftCertificate.setId(newId);
        giftCertificateRepo.setGiftCertificateTags(giftCertificate);
        return newId;
    }

    private void createTags(GiftCertificate giftCertificate) {
        for (Tag tag : giftCertificate.getTags()) {
            Tag tagFromDb = tagRepo.findByName(tag);
            if (isNull(tagFromDb.getId())) {
                Long newId = tagRepo.create(tag);
                tag.setId(newId);
            } else {
                tag.setId(tagFromDb.getId());
            }
        }
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        giftCertificateRepo.delete(giftCertificate);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {

        if (giftCertificate.getId() == null) {
            return;
        }

        setNewFieldValues(giftCertificate);

        tagService.updateList(giftCertificate.getTags());
        giftCertificateRepo.update(giftCertificate);
    }

    private void setNewFieldValues(GiftCertificate giftCertificate) {
        GiftCertificate giftCertificateFromDatabase = findById(giftCertificate.getId());

        String name = giftCertificate.getName();
        if (isNull(name)) {
            giftCertificate.setName(giftCertificateFromDatabase.getName());
        }
        String description = giftCertificate.getDescription();
        if (isNull(description)) {
            giftCertificate.setDescription(giftCertificateFromDatabase.getDescription());
        }
        BigDecimal price = giftCertificate.getPrice();
        if (isNull(price)) {
            giftCertificate.setPrice(giftCertificateFromDatabase.getPrice());
        }
        LocalDateTime createDate = giftCertificate.getCreateDate();
        if (isNull(createDate)) {
            giftCertificate.setCreateDate(giftCertificateFromDatabase.getCreateDate());
        }
        LocalDateTime lastUpdateDate = giftCertificate.getLastUpdateDate();
        if (isNull(lastUpdateDate)) {
            giftCertificate.setLastUpdateDate(giftCertificateFromDatabase.getLastUpdateDate());
        }
        Integer duration = giftCertificate.getDuration();
        if (isNull(duration)) {
            giftCertificate.setDuration(giftCertificateFromDatabase.getDuration());
        }
        List<Tag> tags = giftCertificate.getTags();//todo при получении тегов без айƒи из сертификата с юјй надо ставить им айƒи
        if (isNull(tags)) {
            giftCertificate.setTags(giftCertificateFromDatabase.getTags());
        }
    }

    @Override
    public GiftCertificate findById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepo.findById(id);
        List<Tag> tags = tagRepo.findTagsByGiftCertificateId(id);
        giftCertificate.setTags(tags);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepo.findAll();
    }
}

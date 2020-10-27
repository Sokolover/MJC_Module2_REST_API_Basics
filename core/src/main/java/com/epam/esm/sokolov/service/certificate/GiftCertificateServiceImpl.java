package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepo;
import com.epam.esm.sokolov.repository.tag.TagRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepo giftCertificateRepo;
    private TagRepo tagRepo;

    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo, TagRepo tagRepo) {
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
            if (Objects.isNull(tagFromDb.getId())) {
                Long newId = tagRepo.create(tag);
                tag.setId(newId);
            }else{
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
        giftCertificateRepo.update(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        return giftCertificateRepo.findById(id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepo.findAll();
    }
}

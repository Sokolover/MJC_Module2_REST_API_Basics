package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.converter.GiftCertificateConverter;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepository;
import com.epam.esm.sokolov.repository.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateConverter giftCertificateConverter;
    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateConverter giftCertificateConverter, GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository) {
        this.giftCertificateConverter = giftCertificateConverter;
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Long create(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = giftCertificateConverter.convert(giftCertificateDTO);
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
    public void delete(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = giftCertificateConverter.convert(giftCertificateDTO);
        giftCertificateRepository.deleteGiftCertificatesToTags(giftCertificate);
        giftCertificateRepository.delete(giftCertificate);
    }

    @Override
    @Transactional
    public void update(GiftCertificateDTO giftCertificateDTO) {

        if (giftCertificateDTO.getId() == null) {
            return;
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convert(giftCertificateDTO);

//        giftCertificate.getTags()
//                .forEach(tagRepository::create);

//        for (Tag tag : giftCertificate.getTags()) {
//            Long id = tagRepository.create(tag);
//            tag.setId(id);
//        }

        giftCertificate.getTags().
                forEach(tag -> tag.setId(tagRepository.create(tag)));

//        List<Tag> tagsFromDatabase = tagRepository.findTagsByGiftCertificateId(giftCertificate.getId());
//        setTags(giftCertificate, tagsFromDatabase);
//        tagRepository.updateList(giftCertificate.getTags());

        resetGiftCertificatesToTagsLinks(giftCertificate);
        giftCertificateRepository.update(giftCertificate);
    }

//    private void setTags(GiftCertificate giftCertificate, List<Tag> tagsFromDatabase) {
//        List<Tag> tags = giftCertificate.getTags();
//        if (isNull(tags)) {
//            giftCertificate.setTags(tagsFromDatabase);
//        } else {
//            updateTags(tags);
//        }
//    }
//
//    private void updateTags(List<Tag> tags) {
//        for (Tag tag : tags) {
//            Tag findByNameTag = tagRepository.findByName(tag);
//            if (isNull(findByNameTag.getId())) {
//                createNewTag(tag);
//            } else {
//                updateTag(tag, findByNameTag);
//            }
//        }
//    }
//
//    private void updateTag(Tag tag, Tag findByNameTag) {
//        if (findByNameTag.getId().equals(tag.getId())) {
//            tagRepository.update(tag);
//        } else {
//            tag.setId(findByNameTag.getId());
//        }
//    }

    private void createNewTag(Tag tag) {
        Long newId = tagRepository.create(tag);
        tag.setId(newId);
    }

    private void resetGiftCertificatesToTagsLinks(GiftCertificate giftCertificate) {
        giftCertificateRepository.deleteGiftCertificatesToTags(giftCertificate);
        giftCertificateRepository.setGiftCertificatesToTags(giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDTO findById(long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id);
        List<Tag> tags = tagRepository.findTagsByGiftCertificateId(id);
        giftCertificate.setTags(tags);
        return giftCertificateConverter.convert(giftCertificate);
    }

    @Override
    @Transactional
    public List<GiftCertificateDTO> findAll() {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        setTagsToGiftCertificates(giftCertificates);
        return giftCertificates
                .stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
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

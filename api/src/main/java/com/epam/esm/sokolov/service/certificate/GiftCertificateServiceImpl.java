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
    @Transactional//todo посмотреть как аннотация ведёт себя с checked unchecked exception
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
        tags.forEach(tag -> {
            Tag tagFromDb = tagRepository.findByName(tag);
            if (isNull(tagFromDb.getId())) {
                createNewTag(tag);
            } else {
                tag.setId(tagFromDb.getId());
            }
        });
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
//todo isNull в основном используется в стримах
        if (isNull(giftCertificateDTO.getId())) {
            return;
        }
        GiftCertificate giftCertificate = giftCertificateConverter.convert(giftCertificateDTO);

        giftCertificate.getTags().
                forEach(tag -> tag.setId(tagRepository.create(tag)));

        resetGiftCertificatesToTagsLinks(giftCertificate);
        giftCertificateRepository.update(giftCertificate);
    }

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
        giftCertificates.forEach(giftCertificate -> {
            List<Tag> tags = tagRepository.findTagsByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTags(tags);
        });
    }

    @Override
    public List<GiftCertificateDTO> findAllByParams(Map<String, String> paramMap) {
        List<GiftCertificate> giftCertificates;
        if (CollectionUtils.isEmpty(paramMap)) {
            giftCertificates = giftCertificateRepository.findAll();
        } else {
            giftCertificates = giftCertificateRepository.findAllByParams(paramMap);
        }
        setTagsToGiftCertificates(giftCertificates);
        return giftCertificates
                .stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }
}

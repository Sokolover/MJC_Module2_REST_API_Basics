package com.epam.esm.sokolov.converter;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.dto.TagDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.sokolov.converter.TagConverter.convertTagsDtosFromTag;
import static com.epam.esm.sokolov.converter.TagConverter.convertTagsFromTagDtos;

/**
 * Created by.
 *
 * @author Galina Kutash.
 */
@Service
public class GiftCertificateConverter {

    public GiftCertificateDTO convert(GiftCertificate source) {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
        giftCertificateDTO.setId(source.getId());
        giftCertificateDTO.setName(source.getName());
        giftCertificateDTO.setCreateDate(DateConverter.getZonedDateTime(source.getCreateDate(), source.getCreateDateTimeZone()));
        giftCertificateDTO.setLastUpdateDate(DateConverter.getZonedDateTime(source.getLastUpdateDate(), source.getLastUpdateDateTimeZone()));
        giftCertificateDTO.setDescription(source.getDescription());
        giftCertificateDTO.setPrice(source.getPrice());
        giftCertificateDTO.setTags(convertTagsDtosFromTag(source.getTags()));
        giftCertificateDTO.setDuration(source.getDuration());
        return giftCertificateDTO;
    }

    public GiftCertificate convert(GiftCertificateDTO source) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(source.getId());
        giftCertificate.setName(source.getName());
        giftCertificate.setCreateDate(DateConverter.getLocalDate(source.getCreateDate()));
        giftCertificate.setCreateDateTimeZone(source.getCreateDate().getZone().toString());
        giftCertificate.setLastUpdateDate(DateConverter.getLocalDate(source.getLastUpdateDate()));
        giftCertificate.setLastUpdateDateTimeZone(source.getLastUpdateDate().getZone().toString());
        giftCertificate.setDescription(source.getDescription());
        giftCertificate.setPrice(source.getPrice());
        giftCertificate.setTags(convertTagsFromTagDtos(source.getTags()));
        giftCertificate.setDuration(source.getDuration());
        return giftCertificate;
    }
}

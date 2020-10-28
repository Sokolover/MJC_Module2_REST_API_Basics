package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GiftCertificateRepository {

    Long create(GiftCertificate entity);

    void delete(GiftCertificate entity);

    void update(GiftCertificate entity);

    GiftCertificate findById(long id);

    List<GiftCertificate> findAll();

    void setGiftCertificatesToTags(GiftCertificate giftCertificate);

    void deleteGiftCertificatesToTags(GiftCertificate giftCertificate);

    List<GiftCertificate> findAllByParams(Map<String, String> paramMap);
}

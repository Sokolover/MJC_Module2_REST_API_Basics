package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepo {

    void createGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(GiftCertificate giftCertificate);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate findById(long id);

    List<GiftCertificate> findAll();

}

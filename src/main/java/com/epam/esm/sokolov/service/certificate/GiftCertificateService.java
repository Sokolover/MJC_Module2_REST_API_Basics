package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftCertificateService {

    void createGiftCertificate(GiftCertificate giftCertificate);

    void deleteGiftCertificate(GiftCertificate giftCertificate);

    void updateGiftCertificate(GiftCertificate giftCertificate);

    GiftCertificate findById(long id);

    List<GiftCertificate> findAll();
}

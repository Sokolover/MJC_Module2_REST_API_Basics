package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.repository.certificate.GiftCertificateRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepo giftCertificateRepo;

    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo) {
        this.giftCertificateRepo = giftCertificateRepo;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateRepo.createGiftCertificate(giftCertificate);
    }

    @Override
    public void deleteGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateRepo.deleteGiftCertificate(giftCertificate);
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificateRepo.updateGiftCertificate(giftCertificate);
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

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
    public Long create(GiftCertificate giftCertificate) {
        return giftCertificateRepo.create(giftCertificate);
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

package com.epam.esm.sokolov.repository.certificate;

import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepo {

    Long create(GiftCertificate entity);

    void delete(GiftCertificate entity);

    void update(GiftCertificate entity);

    GiftCertificate findById(long id);

    List<GiftCertificate> findAll();

}

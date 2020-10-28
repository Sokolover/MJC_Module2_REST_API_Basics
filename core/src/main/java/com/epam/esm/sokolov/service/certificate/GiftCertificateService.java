package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.core.GenericService;
import com.epam.esm.sokolov.model.GiftCertificate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GiftCertificateService extends GenericService<GiftCertificate> {

    List<GiftCertificate> findAllByParams(Map<String, String> paramMap);
}

package com.epam.esm.sokolov.service.certificate;

import com.epam.esm.sokolov.service.GenericService;
import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GiftCertificateService extends GenericService<GiftCertificateDTO> {

    List<GiftCertificateDTO> findAllByParams(Map<String, String> paramMap);
}

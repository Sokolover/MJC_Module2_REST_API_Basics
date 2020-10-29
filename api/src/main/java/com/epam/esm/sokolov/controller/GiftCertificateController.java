package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.dto.GiftCertificateDTO;
import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gift_certificate")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/search")
    public List<GiftCertificate> findAll(@RequestParam Map<String, String> allParams) {
        return giftCertificateService.findAllByParams(allParams);
    }

    @GetMapping
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateService.findAll();
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @PostMapping
    public void create(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.create(giftCertificate);
    }

    @PutMapping("/update")
    public void update(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.update(giftCertificate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody GiftCertificateDTO giftCertificate) {
        giftCertificateService.delete(giftCertificate);
    }
}

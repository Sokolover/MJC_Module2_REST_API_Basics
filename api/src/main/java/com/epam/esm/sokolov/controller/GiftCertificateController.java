package com.epam.esm.sokolov.controller;

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

    //    Get certificates with tags (all params are optional and can be used in conjunction):
    //      - by tag name (ONE tag)
    //      - search by part of name/description (can be implemented, using DB function call)
    //      - sort by date/name ASC/DESC
    @GetMapping
    public List<GiftCertificate> findAll(@RequestParam Map<String, String> allParams) {
        return giftCertificateService.findAllByParams(allParams);
    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    //  "createDate":"2020-10-23T12:37:39",
    //  "lastUpdateDate":"2020-10-23T15:37:39",
    @PostMapping
    public void create(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.create(giftCertificate);
    }

    @PutMapping("/update")
    public void update(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.update(giftCertificate);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.delete(giftCertificate);
    }
}

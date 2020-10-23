package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.model.GiftCertificate;
import com.epam.esm.sokolov.service.certificate.GiftCertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift_certificate")
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> findAll() {

        return giftCertificateService.findAll();
    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {

        return giftCertificateService.findById(id);
    }

//        "createDate":"2020-10-23T12:37:39",
//        "lastUpdateDate":"2020-10-23T15:37:39",
    @PostMapping
    public void createTag(@RequestBody GiftCertificate giftCertificate) {

        giftCertificateService.createGiftCertificate(giftCertificate);
        System.out.println("done");
    }
}

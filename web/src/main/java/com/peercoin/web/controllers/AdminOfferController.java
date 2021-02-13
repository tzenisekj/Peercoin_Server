package com.peercoin.web.controllers;

import com.peercoin.web.models.Offer;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.services.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/admin/offer")
public class AdminOfferController {
    @Autowired
    private IOfferService offerService;
    @Autowired
    private OfferRepository offerRepository;
    @GetMapping
    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }
}

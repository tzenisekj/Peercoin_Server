package com.peercoin.web.controllers;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.displayObjects.OfferDisplayObject;
import com.peercoin.web.models.dtos.MessageDto;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.repositories.OrderRepository;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IMessageService;
import com.peercoin.web.services.IOfferService;
import com.peercoin.web.services.IOrderService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/offer")
public class OfferController {
    private Logger logger = Logger.getLogger(OfferController.class.getName());

    @Autowired
    private IOfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IMessageService messageService;

    @GetMapping
    public BuyAndSellOffers offers(Authentication authentication) {
        User user = userRepository.getByUsername(((UserDetails)authentication.getPrincipal()).getUsername());
        List<Offer> buyerOffers = offerRepository.getByBuyer(user.getId());
        List<Offer> sellerOffers = offerRepository.getBySeller(user.getId());
        List<OfferDisplayObject> BODOs = new ArrayList<>();
        List<OfferDisplayObject> SODOs = new ArrayList<>();
        for (Offer offer : buyerOffers) {
            if (!offer.isCompleted()) {
                User seller = userRepository.findById(offer.getSeller()).get();
                BODOs.add(new OfferDisplayObject(offer, user, seller));
            }
        }
        for (Offer offer : sellerOffers) {
            if (!offer.isCompleted()) {
                User buyer = userRepository.findById(offer.getSeller()).get();
                SODOs.add(new OfferDisplayObject(offer, buyer, user));
            }
        }
        return new BuyAndSellOffers(BODOs, SODOs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OfferDisplayObject> getOffer(@PathVariable("id") String id, Authentication authentication) {
        Offer offer = offerRepository.getById(id);
        if (offer == null) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());

        if (!offer.getSeller().equals(user.getId()) || !offer.getBuyer().equals(user.getId())) {
            return ResponseEntity.badRequest().body(null);
        }
        User buyer = userRepository.findById(offer.getBuyer()).orElse(null);
        User seller = userRepository.findById(offer.getSeller()).orElse(null);
        OfferDisplayObject odo = new OfferDisplayObject(offer, buyer, seller);
        return ResponseEntity.ok(odo);
    }

    private static class BuyAndSellOffers {
        List<OfferDisplayObject> buyOffers;
        List<OfferDisplayObject> sellOffers;
        BuyAndSellOffers(List<OfferDisplayObject> buyOffers, List<OfferDisplayObject> sellOffers) {
            this.buyOffers = buyOffers;
            this.sellOffers = sellOffers;
        }

        public List<OfferDisplayObject> getBuyOffers() {
            return buyOffers;
        }

        public void setBuyOffers(List<OfferDisplayObject> buyOffers) {
            this.buyOffers = buyOffers;
        }

        public List<OfferDisplayObject> getSellOffers() {
            return sellOffers;
        }

        public void setSellOffers(List<OfferDisplayObject> sellOffers) {
            this.sellOffers = sellOffers;
        }
    }
}

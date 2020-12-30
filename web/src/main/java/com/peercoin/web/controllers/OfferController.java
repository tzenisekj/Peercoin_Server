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

@Controller
@SuppressWarnings("unused")
@RequestMapping("/offer")
public class OfferController {
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private IOfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IMessageService messageService;

    @GetMapping("{id}/chat")
    public String chat(@PathVariable("id") String id, Model model, Authentication authentication) {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        model.addAttribute("username",userDetails.getUsername());
        Offer offer = offerRepository.getById(id);
        User user = userRepository.getByUsername(((UserDetails)authentication.getPrincipal()).getUsername());
        model.addAttribute("user", user);
        model.addAttribute("offer", offer);
        model.addAttribute("isBuyer", user.getId().equals(offer.getBuyer()));
        model.addAttribute("messageDto", new MessageDto());
        return "messages";
    }

    @GetMapping("/all")
    public String offers(Model model, Authentication authentication) {
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
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("buyerOffers", BODOs);
        model.addAttribute("sellerOffers", SODOs);
        return "offers";
    }
}

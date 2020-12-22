package com.peercoin.web.controllers;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
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
        model.addAttribute("messageDto", new MessageDto());
        return "messages";
    }

    @GetMapping("/all")
    public String offers(Model model, Authentication authentication) {
        List<Offer> buyerOffers = offerRepository.getByBuyer(userRepository.getByUsername(((UserDetails)authentication.getPrincipal()).getUsername()));
        List<Offer> sellerOffers = offerRepository.getBySeller(userRepository.getByUsername(((UserDetails)authentication.getPrincipal()).getUsername()));
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("buyerOffers", buyerOffers);
        model.addAttribute("sellerOffers", sellerOffers);
        return "offers";
    }
}

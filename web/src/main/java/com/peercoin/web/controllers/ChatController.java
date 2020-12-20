package com.peercoin.web.controllers;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.MessageDto;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IMessageService;
import com.peercoin.web.services.IOfferService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/v1/chat")
public class ChatController {
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private IOfferService offerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IMessageService messageService;

    @PostMapping("/{offerId}")
    public ResponseEntity sendMessage(@PathVariable("offerId") String offerId, @RequestBody MessageDto message) {
        Offer offer = offerRepository.getById(offerId);
        if (offer == null) {
            return ResponseEntity.badRequest().body("Offer does not exist");
        }
        User sender = userRepository.getByUsername(message.getSender());
        if (sender == null) {
            if (!(sender.getId().equals(offer.getBuyer().getId()) || sender.getId().equals(offer.getSeller().getId()))) {
                return ResponseEntity.status(Response.SC_FORBIDDEN).body(sender.getUsername() + " not authorized to send a message here");
            }
            return ResponseEntity.status(Response.SC_FORBIDDEN).body("No user found with this username");
        }
        Message builtMessage = new Message();
        builtMessage.setContents(message.getMessage());
        builtMessage.setSender(sender);
        messageService.sendMessage(offer, builtMessage);
        logger.log(Level.WARNING, "message sent");
        return ResponseEntity.ok("{\"response\":\"message sent\"}");
    }

    @GetMapping("/{offerId}")
    public List<Message> receiveMessages(@PathVariable("offerId") String offerId, Authentication authentication) {
        Offer offer = offerRepository.getById(offerId);
        if (offer == null) {
            return null;
        }
        return messageService.getMessageHistory(offer);
    }
}

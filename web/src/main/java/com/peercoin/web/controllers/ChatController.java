package com.peercoin.web.controllers;

import com.peercoin.core.currency.exceptions.InsufficientFundsException;
import com.peercoin.core.currency.exceptions.InvalidStateException;
import com.peercoin.web.models.Offer;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.EscrowResponseDto;
import com.peercoin.web.models.dtos.MessageDto;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IEscrowService;
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

    @Autowired
    private IEscrowService escrowService;

    @PostMapping("/{offerId}")
    public ResponseEntity sendMessage(@PathVariable("offerId") String offerId, @RequestHeader("credentials") String credentials, @RequestBody MessageDto message) {
        Offer offer = offerRepository.getById(offerId);
        if (offer == null) {
            return ResponseEntity.badRequest().body("Offer does not exist");
        }
        User sender = userRepository.getByRestApiKey(credentials);
        if (sender == null) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).body("No user found with this username");
        }
        if (!(sender.getId().equals(offer.getBuyer()) || sender.getId().equals(offer.getSeller()))) {
            return ResponseEntity.status(Response.SC_FORBIDDEN).body(sender.getUsername() + " not authorized to send a message here");
        }
        Message builtMessage = new Message();
        builtMessage.setContents(message.getMessage());
        builtMessage.setSender(sender);
        messageService.sendMessage(offer, builtMessage);
        logger.log(Level.INFO, "message sent");
        return ResponseEntity.ok("{\"response\":\"message sent\"}");
    }

    @GetMapping("/{offerId}")
    public List<Message> receiveMessages(@PathVariable("offerId") String offerId, Authentication authentication) {
        Offer offer = offerRepository.getById(offerId);
        if (offer == null) {
            return null;
        }
        if (authentication == null) {
            return null;
        }
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        if (user == null) {
            return null;
        }
        return messageService.getMessageHistory(offer);
    }

    @PostMapping("/{offerId}/escrow")
    public EscrowResponseDto escrow(@PathVariable("offerId") String offerId, @RequestHeader("credentials") String credentials) {
        User user = userRepository.getByRestApiKey(credentials);
        if (user == null) {
            return null;
        }

        EscrowResponseDto response = new EscrowResponseDto();
        try {
            Offer offer = offerRepository.getById(offerId);
            User seller = userRepository.findById(offer.getSeller()).get();
            if (seller.getId().equals(user.getId())) {
                boolean success = escrowService.escrow(offer);
                if (success) {
                    response.status = "Ok";
                    return response;
                }
            } else {
                response.status = "Invalid User";
                return response;
            }
        } catch (InsufficientFundsException | InvalidStateException e) {
            response.status = e.getMessage();
            return response;
        }
        response.status="Failed to Execute";
        return response;
    }

    @PostMapping("/{offerId}/marksent")
    public EscrowResponseDto send(@PathVariable("offerId") String offerId, @RequestHeader("credentials") String credentials, Authentication authentication) {
        User user = userRepository.getByRestApiKey(credentials);
        EscrowResponseDto response = new EscrowResponseDto();
        response.status = "Failed to Execute";
        try {
            Offer offer = offerRepository.getById(offerId);
            User buyer = userRepository.findById(offer.getBuyer()).get();
            if (buyer.getId().equals(user.getId())) {
                boolean success = escrowService.paymentSent(offer);
                if (success) {
                    response.status = "Ok";
                }
            } else {
                response.status = "Invalid User";
            }
        } catch (InvalidStateException e) {
            response.status = e.getMessage();
        }
        return response;
    }

    @PostMapping("{offerId}/markreceived")
    public EscrowResponseDto receive(@PathVariable("offerId") String offerId, @RequestHeader("credentials") String credentials) {
        User user = userRepository.getByRestApiKey(credentials);
        EscrowResponseDto response = new EscrowResponseDto();
        response.status = "Failed to Execute";
        try {
            Offer offer = offerRepository.getById(offerId);
            User seller = userRepository.findById(offer.getSeller()).get();
            if (seller.getId().equals(user.getId())) {
                boolean success = escrowService.paymentReceived(offer);
                if (success) {
                    response.status = "Ok";
                }
            } else {
                response.status = "Invalid User";
            }
        } catch (InvalidStateException e) {
            response.status = e.getMessage();
        }
        return response;
    }

    @PostMapping("/{offerId}/investigation")
    public EscrowResponseDto openInvestigation(@PathVariable("offerId") String offerId, @RequestHeader("credentials") String credentials) {
        User user = userRepository.getByRestApiKey(credentials);
        EscrowResponseDto response = new EscrowResponseDto();
        response.status = "Failed to Execute";
        try {
            Offer offer = offerRepository.getById(offerId);
            User seller = userRepository.getByUsername(offer.getSeller());
            if (seller.getId().equals(user.getId())) {
                boolean success = escrowService.paymentSent(offer);
                if (success) {
                    response.status = "Ok";
                }
            } else {
                response.status = "Invalid User";
            }
        } catch (InvalidStateException e) {
            response.status = e.getMessage();
        }
        return response;
    }
}

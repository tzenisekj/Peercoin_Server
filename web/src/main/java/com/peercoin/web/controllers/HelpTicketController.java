package com.peercoin.web.controllers;

import com.peercoin.web.models.HelpTicket;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.HelpTicketMessageDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.responses.SuccessResponses;
import com.peercoin.web.services.implementations.HelpTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/helpticket")
public class HelpTicketController {
    @Autowired
    private HelpTicketService helpTicketService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/raise")
    public ResponseEntity<String> raise(@RequestBody HelpTicketMessageDto messageDto, Authentication authentication) {
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        HelpTicket res = helpTicketService.raise(user, messageDto.getMessage());
        return SuccessResponses.success("Help Ticket " + res.getId() + " raised");
    }
}

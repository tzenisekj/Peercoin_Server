package com.peercoin.web.controllers;

import com.peercoin.web.models.HelpTicket;
import com.peercoin.web.models.dtos.NoteDto;
import com.peercoin.web.models.dtos.TicketHandlerDto;
import com.peercoin.web.responses.FailureResponses;
import com.peercoin.web.responses.SuccessResponses;
import com.peercoin.web.services.implementations.HelpTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin/helpticket")
public class AdminHelpTicketController {
    @Autowired
    private HelpTicketService helpTicketService;

    @GetMapping
    public List<HelpTicket> getTickets(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "userid", required = false) String userid) {
        if (userid == null) {
            if (id == null) {
                return helpTicketService.getAllHelpTickets();
            }
            return helpTicketService.getAllById(id);
        }
        return helpTicketService.getAllByUser(userid);
    }

    @PutMapping("/handle")
    public ResponseEntity<String> handleTicket(@RequestBody @Valid TicketHandlerDto ticketHandlerDto) {
        try {
            HelpTicket helpTicket;
            if (ticketHandlerDto.getNote() != null) {
                helpTicket = helpTicketService.handle(ticketHandlerDto.getTicketId(), ticketHandlerDto.getStatus(), ticketHandlerDto.getNote());
            } else {
                helpTicket = helpTicketService.handle(ticketHandlerDto.getTicketId(), ticketHandlerDto.getStatus());
            }
            return SuccessResponses.success("Help Ticket " + helpTicket.getId() + " moved to status " + helpTicket.getHelpTicketStatus());
        } catch (NoSuchElementException e) {
            return FailureResponses.failure("Could not find an element in the request");
        }
    }

    @PutMapping("/note")
    public ResponseEntity<String> notateTicket(@RequestBody @Valid NoteDto noteDto) {
        try {
            HelpTicket helpTicket = helpTicketService.addNote(noteDto.getTicketId(), noteDto.getNote());
            return SuccessResponses.success("Note added to " + helpTicket.getId());
        } catch (NoSuchElementException e) {
            return FailureResponses.failure("Could not find an element in the request");
        }
    }
}

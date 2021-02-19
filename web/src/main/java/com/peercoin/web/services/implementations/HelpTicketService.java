package com.peercoin.web.services.implementations;

import com.peercoin.web.enums.HelpTicketStatus;
import com.peercoin.web.models.HelpTicket;
import com.peercoin.web.models.User;
import com.peercoin.web.repositories.HelpTicketRepository;
import com.peercoin.web.services.IHelpTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@SuppressWarnings("unused")
@Transactional
public class HelpTicketService implements IHelpTicketService {
    @Autowired
    private HelpTicketRepository helpTicketRepository;

    @Override
    public HelpTicket raise(User user, String message) {
        HelpTicket helpTicket = new HelpTicket();
        helpTicket.setUserId(user.getId());
        helpTicket.setMessage(message);
        return helpTicketRepository.insert(helpTicket);
    }

    @Override
    public HelpTicket raise(User user, String message, String offerId) {
        HelpTicket helpTicket = new HelpTicket();
        helpTicket.setUserId(user.getId());
        helpTicket.setMessage(message);
        helpTicket.setOfferId(offerId);
        return helpTicketRepository.insert(helpTicket);
    }

    @Override
    public HelpTicket handle(String ticketId, int statusInt) throws NoSuchElementException {
        HelpTicketStatus status = determineStatus(statusInt);
        HelpTicket helpTicket = helpTicketRepository.findById(ticketId).orElseThrow();
        helpTicket.setHelpTicketStatus(status);
        helpTicket.setResolved(determineHelpTicketResolution(status));
        return helpTicketRepository.save(helpTicket);
    }

    @Override
    public HelpTicket handle(String ticketId, int statusInt, String note) throws NoSuchElementException {
        HelpTicketStatus status = determineStatus(statusInt);
        HelpTicket helpTicket = helpTicketRepository.findById(ticketId).orElseThrow();
        helpTicket.setHelpTicketStatus(status);
        helpTicket.addNote(note);
        helpTicket.setResolved(determineHelpTicketResolution(status));
        return helpTicketRepository.save(helpTicket);
    }

    @Override
    public HelpTicket addNote(String ticketId, String note) throws NoSuchElementException {
        HelpTicket helpTicket = helpTicketRepository.findById(ticketId).orElseThrow();
        helpTicket.addNote(note);
        return helpTicketRepository.save(helpTicket);
    }

    @Override
    public HelpTicket removeNote(String ticketId, int index) throws NoSuchElementException {
        HelpTicket helpTicket = helpTicketRepository.findById(ticketId).orElseThrow();
        helpTicket.removeNote(index);
        return helpTicketRepository.save(helpTicket);
    }

    @Override
    public List<HelpTicket> getAllHelpTickets() {
        return helpTicketRepository.findAll();
    }

    public List<HelpTicket> getAllById(String id) {
        List<HelpTicket> helpTickets = new ArrayList<>();
        helpTickets.add(helpTicketRepository.findById(id).orElse(null));
        return helpTickets;
    }

    @Override
    public List<HelpTicket> getAllByUser(String userId) {
        return helpTicketRepository.getByUserId(userId);
    }

    private HelpTicketStatus determineStatus(int status) {
        switch(status) {
            case 0:
                return HelpTicketStatus.OPEN;
            case 1:
                return HelpTicketStatus.CLAIMED;
            case 2:
                return HelpTicketStatus.PENDING_USER_ACTION;
            case 3:
                return HelpTicketStatus.CLOSED;
            default:
                throw new NoSuchElementException();
        }
    }

    private boolean determineHelpTicketResolution(HelpTicketStatus helpTicketStatus) {
        return helpTicketStatus == HelpTicketStatus.CLOSED;
    }
}

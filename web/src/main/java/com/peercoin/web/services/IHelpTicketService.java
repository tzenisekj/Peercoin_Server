package com.peercoin.web.services;

import com.peercoin.web.enums.HelpTicketStatus;
import com.peercoin.web.models.HelpTicket;
import com.peercoin.web.models.User;

import java.util.List;
import java.util.NoSuchElementException;

public interface IHelpTicketService {
    HelpTicket raise(User user, String message);
    HelpTicket raise(User user, String message, String offerId);
    HelpTicket handle(String ticketId, int statusInt) throws NoSuchElementException;
    HelpTicket handle(String ticketId, int statusInt, String note) throws NoSuchElementException;
    HelpTicket addNote(String ticketId, String note) throws NoSuchElementException;
    HelpTicket removeNote(String ticketId, int index) throws NoSuchElementException;
    List<HelpTicket> getAllHelpTickets();
    List<HelpTicket> getAllById(String id);
    List<HelpTicket> getAllByUser(String userId);
}

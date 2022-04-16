package com.peercoin.web.models;

import com.peercoin.web.enums.HelpTicketStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class HelpTicket {
    @Id
    private String id;

    private String userId;
    private String offerId;
    private String message;
    private HelpTicketStatus helpTicketStatus;
    private List<String> notes;
    private boolean resolved;

    public HelpTicket() {
        helpTicketStatus = HelpTicketStatus.OPEN;
        notes = new ArrayList<>();
        resolved = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HelpTicketStatus getHelpTicketStatus() {
        return helpTicketStatus;
    }

    public void setHelpTicketStatus(HelpTicketStatus helpTicketStatus) {
        this.helpTicketStatus = helpTicketStatus;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public void removeNote(int index) {
        notes.remove(index);
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}

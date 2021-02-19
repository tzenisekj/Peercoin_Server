package com.peercoin.web.models.dtos;

import javax.validation.constraints.NotNull;

public class NoteDto {
    @NotNull
    private String note;

    @NotNull
    private String ticketId;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}

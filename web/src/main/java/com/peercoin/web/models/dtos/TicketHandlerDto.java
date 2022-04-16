package com.peercoin.web.models.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TicketHandlerDto {
    @NotNull
    @NotEmpty
    private String ticketId;

    @NotNull
    private int status;

    private String note;

    public TicketHandlerDto() { }

    public TicketHandlerDto(String ticketId, int status, String note) {
        this.ticketId = ticketId;
        this.status = status;
        this.note = note;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

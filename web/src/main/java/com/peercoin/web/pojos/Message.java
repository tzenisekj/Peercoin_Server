package com.peercoin.web.pojos;

import com.peercoin.web.models.User;

import java.util.Date;

public class Message {
    private User sender;
    private String contents;
    private Date dateTimeSent;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(Date dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }
}

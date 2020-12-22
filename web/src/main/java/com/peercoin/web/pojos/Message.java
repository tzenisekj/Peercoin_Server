package com.peercoin.web.pojos;

import com.peercoin.web.models.Notifiable;
import com.peercoin.web.models.User;

import java.util.Date;

public class Message implements Notifiable {
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

    @Override
    public String notificationMessage() {
        // 20 chars to start with
        String temp;
        if(contents.length() <= 20){
            temp = contents;
        }
        else{
            temp = getContents().substring(0, 20) + "...";
        }
        return "Message from " + getSender().getUsername() + ": " + temp;
    }
}

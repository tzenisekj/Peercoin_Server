package com.peercoin.web.models;

import com.peercoin.web.pojos.Message;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Offer {
    @Id
    private String id;

    private String buyer;
    private String seller;

    private double amount;

    private Order order;

    private boolean escrowed;
    private boolean sent;
    private boolean received;
    private boolean openInvestigation;
    private boolean completed;

    private ArrayList<Message> conversation;

    public Offer() {
        conversation = new ArrayList<>();
        escrowed = false;
        sent = false;
        received = false;
        openInvestigation = false;
        completed = false;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Message> getConversation() {
        return conversation;
    }

    public void addMessage(Message message) {
        this.conversation.add(message);
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isEscrowed() {
        return escrowed;
    }

    public void setEscrowed(boolean escrowed) {
        this.escrowed = escrowed;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isOpenInvestigation() {
        return openInvestigation;
    }

    public void setOpenInvestigation(boolean openInvestigation) {
        this.openInvestigation = openInvestigation;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

package com.peercoin.web.models;

import com.peercoin.web.pojos.Message;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Offer {
    @Id
    private String id;

    private User buyer;
    private User seller;

    private double amount;

    private Order order;

    private ArrayList<Message> conversation;

    public Offer() {
        conversation = new ArrayList<>();
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

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
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
}

package com.peercoin.web.models.displayObjects;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;

public class OfferDisplayObject {
    private String id;

    private User buyer;
    private User seller;

    private double amount;

    private Order order;

    private boolean escrowed;
    private boolean sent;
    private boolean received;
    private boolean openInvestigation;

    public OfferDisplayObject(Offer offer, User buyer, User seller) {
        this.id = offer.getId();
        this.buyer = buyer;
        this.seller = seller;
        this.amount = offer.getAmount();
        this.order = offer.getOrder();
        this.escrowed = offer.isEscrowed();
        this.sent = offer.isSent();
        this.received = offer.isReceived();
        this.openInvestigation = offer.isOpenInvestigation();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

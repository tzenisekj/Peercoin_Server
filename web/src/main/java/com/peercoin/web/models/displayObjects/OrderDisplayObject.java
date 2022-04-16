package com.peercoin.web.models.displayObjects;

import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.pojos.NameTickerEntity;
import com.peercoin.web.pojos.OrderType;

public class OrderDisplayObject {
    private String id;

    private User initiator;

    private boolean buy;

    private NameTickerEntity crypto;
    private NameTickerEntity payment;
    private PaymentMethod method;

    private double exchangeRate;
    private double min;
    private double max;
    private boolean active;
    private boolean removeOnOfferCompletion;

    private OrderType orderType;

    public OrderDisplayObject(Order order, User initiator) {
        this.buy = order.isBuy();
        this.id = order.getId();
        this.initiator = initiator;
        this.crypto = order.getCrypto();
        this.payment = order.getPayment();
        this.method = order.getMethod();
        this.exchangeRate = order.getExchangeRate();
        this.min = order.getMin();
        this.max = order.getMax();
        this.orderType = order.getOrderType();
    }

    public String getId() {
        return id;
    }

    public User getInitiator() {
        return initiator;
    }

    public boolean isBuy() {
        return buy;
    }

    public NameTickerEntity getCrypto() {
        return crypto;
    }

    public NameTickerEntity getPayment() {
        return payment;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public void setCrypto(NameTickerEntity crypto) {
        this.crypto = crypto;
    }

    public void setPayment(NameTickerEntity payment) {
        this.payment = payment;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRemoveOnOfferCompletion() {
        return removeOnOfferCompletion;
    }

    public void setRemoveOnOfferCompletion(boolean removeOnOfferCompletion) {
        this.removeOnOfferCompletion = removeOnOfferCompletion;
    }
}

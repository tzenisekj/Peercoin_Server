package com.peercoin.web.models;

import com.peercoin.core.currency.Currency;
import com.peercoin.web.pojos.OrderType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.paymentmethods.PaymentMethod;

@Document
public class Order {
    @Id
    private String id;

    private User initiator;

    private boolean buy;

    private CryptoCoin crypto;
    private Currency payment;
    private PaymentMethod method;

    private double exchangeRate;
    private double min;
    private double max;

    private OrderType orderType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public CryptoCoin getCrypto() {
        return crypto;
    }

    public void setCrypto(CryptoCoin crypto) {
        this.crypto = crypto;
    }

    public Currency getPayment() {
        return payment;
    }

    public void setPayment(Currency payment) {
        this.payment = payment;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        switch(orderType.toLowerCase()) {
            case "buy":
                this.orderType=OrderType.BUY;
                break;
            case "sell":
                this.orderType=OrderType.SELL;
                break;
        }
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }
}

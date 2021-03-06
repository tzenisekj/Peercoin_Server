package com.peercoin.web.models;

import com.peercoin.core.currency.Currency;
import com.peercoin.web.pojos.NameTickerEntity;
import com.peercoin.web.pojos.OrderType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.paymentmethods.PaymentMethod;

@Document
public class Order {
    @Id
    private String id;

    private String initiator;

    private boolean buy;

    private NameTickerEntity crypto;
    private NameTickerEntity payment;
    private PaymentMethod method;

    private double exchangeRate;
    private double min;
    private double max;

    private OrderType orderType;

    private boolean active;
    private boolean removeOnOfferCompletion;

    public Order() {
        active = true;
        removeOnOfferCompletion = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public NameTickerEntity getCrypto() {
        return crypto;
    }

    public void setCrypto(CryptoCoin crypto) {
        NameTickerEntity nameTickerEntity = new NameTickerEntity();
        nameTickerEntity.ticker = crypto.getTicker();
        nameTickerEntity.name = crypto.getName();
        this.crypto = nameTickerEntity;
    }

    public NameTickerEntity getPayment() {
        return payment;
    }

    public void setPayment(Currency payment) {
        NameTickerEntity nameTickerEntity = new NameTickerEntity();
        nameTickerEntity.name = payment.getName();
        nameTickerEntity.ticker = payment.getTicker();
        this.payment = nameTickerEntity;
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

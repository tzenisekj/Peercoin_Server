package com.peercoin.web.pojos;

public class NameTickerEntity {
    public String name;
    public String ticker;

    public String getName() {
        return this.name;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}

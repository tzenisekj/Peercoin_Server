package com.peercoin.web.pojos;

import com.peercoin.core.currency.CurrencyMethods;

public class WalletContents {
    public double value;
    public String address;
    public WalletContents() {

    }

    public WalletContents(double value, String address) {
        this.value = value;
        this.address = address;
    }
}

package com.peercoin.web.pojos;


import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.paymentmethods.PaymentMethod;

import java.util.List;

public class Info {
    private List<CryptoCoin> cryptocurrencies;
    private List<Fiat> fiats;
    private List<PaymentMethod> methods;

    public Info() { }

    public Info(List<CryptoCoin> cryptocurrencies, List<Fiat> fiats, List<PaymentMethod> methods) {
        this.cryptocurrencies = cryptocurrencies;
        this.fiats = fiats;
        this.methods = methods;
    }

    public List<CryptoCoin> getCryptocurrencies() {
        return cryptocurrencies;
    }

    public void setCryptocurrencies(List<CryptoCoin> cryptocurrencies) {
        this.cryptocurrencies = cryptocurrencies;
    }

    public List<Fiat> getFiats() {
        return fiats;
    }

    public void setFiats(List<Fiat> fiats) {
        this.fiats = fiats;
    }

    public List<PaymentMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<PaymentMethod> methods) {
        this.methods = methods;
    }
}

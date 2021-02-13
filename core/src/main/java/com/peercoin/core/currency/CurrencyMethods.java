package com.peercoin.core.currency;

public interface CurrencyMethods {
    float getBalance();
    String createAddress();
    boolean pay(String destination, double amount);
    float getAddressUnconfirmed(String address);
    float getAddressConfirmed(String address);
    float getLastPrice();
}

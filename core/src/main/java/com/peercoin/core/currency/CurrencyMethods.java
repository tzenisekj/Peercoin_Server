package com.peercoin.core.currency;

public interface CurrencyMethods {
    String getName();
    String getTicker();
    String getClassName();
    float getBalance();
    String createAddress();
    boolean pay(String destination, int amount);
    float getAddressUnconfirmed(String address);
    float getAddressConfirmed(String address);
    float getLastPrice();
}

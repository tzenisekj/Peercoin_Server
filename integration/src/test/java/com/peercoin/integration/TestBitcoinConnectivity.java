package com.peercoin.integration;

import com.peercoin.core.currency.Currency;
import org.junit.jupiter.api.Test;

public class TestBitcoinConnectivity {
    @Test
    public void testCreateAddress() {
        Currency currency = createBitcoinCurrency();

        String tmp = null;
        tmp = currency.getCurrencyMethods().createAddress();
        assert tmp != null;
    }

    @Test
    public void testGetBalance() {
        Currency currency = createBitcoinCurrency();

        float tmp = -1;
        tmp = currency.getCurrencyMethods().getBalance();
        assert tmp == 0.0;
    }

    @Test
    public void getAddressBalance() {
        Currency currency = createBitcoinCurrency();
        String address = currency.getCurrencyMethods().createAddress();
        float confirmed = currency.getCurrencyMethods().getAddressConfirmed(address);
        float unconfirmed = currency.getCurrencyMethods().getAddressUnconfirmed(address);
        assert confirmed == 0.0;
        assert unconfirmed == 0.0;
    }

    public Currency createBitcoinCurrency() {
        CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
        return currencyFactory.createCurrency("Bitcoin");
    }

    @Test
    public void temp() {
        Currency currency = createBitcoinCurrency();
        float currPrice = currency.getCurrencyMethods().getLastPrice();
        assert currPrice != 0.0;
    }
}

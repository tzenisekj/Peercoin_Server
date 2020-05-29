package com.peercoin.core.currency;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CurrencyTest {
    @Test
    public void checkJsonify() {
        Currency currency=new CryptoCoin();
        currency.setName("Bitcoin");
        currency.setTicker("BTC");
        try {
            String currencyJson = currency.toJson();
            System.out.println(currencyJson);
            Currency returnedCurrency = Currency.parseJson(currencyJson);
            System.out.println(returnedCurrency);
            Assert.assertEquals(currency.toString(),returnedCurrency.toString());
        } catch(IOException e) {
            Assert.fail();
        }

    }
}

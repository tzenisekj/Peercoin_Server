package com.peercoin.web.services.helpers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.CurrencyMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CryptoCoinGenerator implements ModelGenerator<CryptoCoin> {
    protected static CryptoCoinGenerator instance;
    protected CryptoCoinGenerator() {

    }
    public static CryptoCoinGenerator getInstance() {
        if (instance == null) {
            instance = new CryptoCoinGenerator();
        }
        return instance;
    }
    @Override
    public CryptoCoin generateOne() {
        CryptoCoin cryptoCoin = new CryptoCoin();
        cryptoCoin.setName("Made Up Crypto" + UUID.randomUUID().toString());
        cryptoCoin.setTicker("MUC" + (int) (Math.random() * 10 + 1));
        cryptoCoin.setCurrencyMethods(new CurrencyMethods() {

            @Override
            public float getBalance() {
                return 0;
            }

            @Override
            public String createAddress() {
                return "fake address";
            }

            @Override
            public boolean pay(String destination, double amount) {
                return false;
            }

            @Override
            public float getAddressUnconfirmed(String address) {
                return 0;
            }

            @Override
            public float getAddressConfirmed(String address) {
                return 0;
            }

            @Override
            public float getLastPrice() {
                return (float) (Math.random()*20000 + 500);
            }
        });
        return cryptoCoin;
    }

    @Override
    public List<CryptoCoin> generateMany() {
        List<CryptoCoin> cryptoCoins = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random()*10 + 2); i++) {
            cryptoCoins.add(generateOne());
        }
        return cryptoCoins;
    }
}

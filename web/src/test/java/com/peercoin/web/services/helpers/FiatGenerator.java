package com.peercoin.web.services.helpers;

import com.peercoin.core.currency.CurrencyMethods;
import com.peercoin.web.models.Fiat;

import java.util.ArrayList;
import java.util.List;

public class FiatGenerator implements ModelGenerator<Fiat> {
    protected static FiatGenerator instance;
    protected FiatGenerator() {

    }
    public static FiatGenerator getInstance() {
        if (instance == null) {
            instance = new FiatGenerator();
        }
        return instance;
    }

    @Override
    public Fiat generateOne() {
        Fiat fiat = new Fiat();
        fiat.setName("My Fake Fiat");
        fiat.setTicker("MFF");
        fiat.setCurrencyMethods(new CurrencyMethods() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getTicker() {
                return null;
            }

            @Override
            public String getClassName() {
                return null;
            }

            @Override
            public float getBalance() {
                return 0;
            }

            @Override
            public String createAddress() {
                return null;
            }

            @Override
            public boolean pay(String destination, int amount) {
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
                return 0;
            }
        });
        return fiat;
    }

    @Override
    public List<Fiat> generateMany() {
        List<Fiat> fiats = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random()*10 + 2); i++) {
            fiats.add(generateOne());
        }
        return fiats;
    }
}

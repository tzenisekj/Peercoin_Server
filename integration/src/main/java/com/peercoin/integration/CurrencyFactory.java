package com.peercoin.integration;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.integration.currency.JythonCurrency;
import com.peercoin.integration.currency.JythonPaymentMethod;

public class CurrencyFactory {
    private static CurrencyFactory instance = null;

    protected CurrencyFactory() {
    }

    public static CurrencyFactory getInstance() {
        if (instance == null) {
            instance = new CurrencyFactory();
        }

        return instance;
    }

    public Currency createCurrency(String moduleName) {
        JythonObjectFactory jythonObjectFactory = JythonObjectFactory.getInstance();
        JythonCurrency jythonCurrency = (JythonCurrency) jythonObjectFactory.createObject(JythonCurrency.class, moduleName);
        Currency currency = null;
        if (jythonCurrency.getClassName().equalsIgnoreCase("crypto")) {
            currency = new CryptoCoin();
            currency.setCurrencyMethods(jythonCurrency.getCurrencyMethods());

        } else if (jythonCurrency.getClassName().equalsIgnoreCase("fiat")) {
            currency = new Fiat();
        }
        assert currency != null;
        currency.setName(jythonCurrency.getName());
        currency.setTicker(jythonCurrency.getTicker());

        return currency;
    }

    public PaymentMethod createPaymentMethod(String moduleName) {
        JythonObjectFactory jythonObjectFactory = JythonObjectFactory.getInstance();
        JythonPaymentMethod jythonPaymentMethod = (JythonPaymentMethod) jythonObjectFactory.createObject(JythonPaymentMethod.class, moduleName);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setName(jythonPaymentMethod.getName());
        return paymentMethod;
    }
}

package com.peercoin.integration;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.paymentmethods.PaymentMethod;
import org.junit.jupiter.api.Test;

public class TestCurrencyFactory {

    @Test
    public void testCreateCurrency() {
        CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
        Currency currency = currencyFactory.createCurrency("Bitcoin");

        assert currency instanceof CryptoCoin;
        assert currency.getTicker().equals("BTC");
        assert currency.getName().equalsIgnoreCase("Bitcoin");
    }

    @Test
    public void testCreatePaymentMethod() {
        CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
        PaymentMethod paymentMethod = currencyFactory.createPaymentMethod("Paypal");

        assert paymentMethod.getName().equalsIgnoreCase("Paypal");
    }
}

package com.peercoin.integration.currency;

import com.peercoin.core.currency.CurrencyMethods;

public interface JythonCurrency {
    String getName();
    String getTicker();
    String getClassName();
    CurrencyMethods getCurrencyMethods();
}

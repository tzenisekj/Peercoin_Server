package com.peercoin.core.currency.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;

public interface ICurrencyService {
    Currency addCurrency(Currency potential) throws CurrencyNameExistsException, CurrencyTickerExistsException;
}

package com.peercoin.web.services.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.FiatRepository;
import com.peercoin.web.services.implementations.CurrencyService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

public class BaseCurrencyServiceTest <T extends CurrencyService, U extends Currency> {
    @InjectMocks
    T service;

    @Mock
    protected CryptoCoinRepository cryptoRepository;

    @Mock
    protected FiatRepository fiatRepository;

    @BeforeTestMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    protected U defaultCurrency;

    public BaseCurrencyServiceTest() {
        super();
    }

    public void addCurrency() throws CurrencyTickerExistsException, CurrencyNameExistsException {
        service.addCurrency(defaultCurrency);
    }
}

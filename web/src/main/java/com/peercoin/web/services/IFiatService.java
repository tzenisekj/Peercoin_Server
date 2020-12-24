package com.peercoin.web.services;

import com.peercoin.core.currency.Fiat;
import com.peercoin.core.currency.services.ICurrencyService;

import java.util.List;

public interface IFiatService extends ICurrencyService {
    List<Fiat> getAllFiat();
}

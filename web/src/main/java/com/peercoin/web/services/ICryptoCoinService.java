package com.peercoin.web.services;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.services.ICurrencyService;

import java.util.List;

public interface ICryptoCoinService extends ICurrencyService {
    List<CryptoCoin> getAllCryptoCoins();
}

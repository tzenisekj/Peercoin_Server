package com.peercoin.web.services;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;
import com.peercoin.web.houseKeepers.NonPersistentRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CryptoCoinService extends CurrencyService implements ICryptoCoinService{

    @Override
    public List<CryptoCoin> getAllCryptoCoins() {
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        List<com.peercoin.web.models.CryptoCoin> stored = super.cryptoRepository.findAll();
        Iterable<CryptoCoin> cryptoCoinIterable = repositories.cryptoRepository.findAll();
        List<CryptoCoin> cryptoCoins= new ArrayList<>();
        cryptoCoinIterable.forEach(cryptoCoins::add);
        cryptoCoins.addAll(stored);
        return cryptoCoins;
    }

    @Override
    public Currency getByName(String name) {
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        List<com.peercoin.web.models.CryptoCoin> stored = super.cryptoRepository.findAll();
        Iterable<CryptoCoin> cryptoCoinIterable = repositories.cryptoRepository.findAll();
        for (com.peercoin.web.models.CryptoCoin coin : stored) {
            if (coin.getName().equalsIgnoreCase(name)) {
                return coin;
            }
        };
        for (CryptoCoin cryptoCoin : cryptoCoinIterable) {
            if (cryptoCoin.getName().equalsIgnoreCase(name)) {
                return cryptoCoin;
            }
        };
        return null;
    }
}

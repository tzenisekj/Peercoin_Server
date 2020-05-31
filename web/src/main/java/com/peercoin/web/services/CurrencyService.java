package com.peercoin.web.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;
import com.peercoin.core.currency.services.ICurrencyService;
import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.models.Fiat;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.FiatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
@SuppressWarnings("unused")
public class CurrencyService implements ICurrencyService {
    private Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private CryptoCoinRepository cryptoRepository;
    @Autowired
    private FiatRepository fiatRepository;

    @Override
    public Currency addCurrency(Currency potential) throws CurrencyNameExistsException, CurrencyTickerExistsException {
        checkCurrencyExistence(potential);
        if (potential instanceof CryptoCoin) {
            cryptoRepository.save(((CryptoCoin) potential));
        }
        if (potential instanceof Fiat) {
            fiatRepository.save(((Fiat) potential));
        }
        return potential;
    }

    public void checkCurrencyExistence(Currency potential) throws CurrencyNameExistsException, CurrencyTickerExistsException {
        checkIfCurrencyNameExists(potential.getName());
        checkIfCurrencyTickerExists(potential.getTicker());
    }

    private void checkIfCurrencyNameExists(String name) throws CurrencyNameExistsException {
        CryptoCoin cryptoCoin = cryptoRepository.getByName(name);
        Fiat fiat = fiatRepository.getByName(name);
        if (cryptoCoin != null || fiat != null) {
            throw new CurrencyNameExistsException("currency " + name + " already exists");
        }
    }

    private void checkIfCurrencyTickerExists(String ticker) throws CurrencyTickerExistsException {
        CryptoCoin crypto=cryptoRepository.getByTicker(ticker);
        Fiat fiat=fiatRepository.getByTicker(ticker);
        if (crypto != null || fiat != null) {
            throw new CurrencyTickerExistsException("ticker " + ticker + " already exists");
        }
    }
}

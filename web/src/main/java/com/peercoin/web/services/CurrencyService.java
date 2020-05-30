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
            CryptoCoin cryptoCoin=(CryptoCoin) potential;
            cryptoRepository.save(cryptoCoin);
            return cryptoCoin;
        }
        if (potential instanceof Fiat) {
            Fiat fiat = (Fiat) potential;
            fiatRepository.save(fiat);
            return fiat;
        }
        return null;
    }

    @Override
    public Currency receiveCurrencyMessage(Object message) {
        System.out.println();

        System.out.println("OBJECT TYPEOF: " + message.toString());
        System.out.println();
        return null;
    }

    private void checkCurrencyExistence(Currency potential) throws CurrencyNameExistsException, CurrencyTickerExistsException {
        CryptoCoin crypto=cryptoRepository.getByName(potential.getName());
        Fiat fiat=fiatRepository.getByName(potential.getName());
        if (crypto != null || fiat != null){
            throw new CurrencyNameExistsException("currency " + potential.getName() + " already exists");
        }
        crypto=cryptoRepository.getByTicker(potential.getTicker());
        fiat=fiatRepository.getByTicker(potential.getTicker());
        if (crypto != null || fiat != null) {
            throw new CurrencyTickerExistsException("ticker " + potential.getTicker() + " already exists");
        }
    }
}

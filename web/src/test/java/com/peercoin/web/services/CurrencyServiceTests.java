package com.peercoin.web.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.models.Fiat;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.FiatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyServiceTests {
    @InjectMocks
    CurrencyService currencyService;

    @Mock
    CryptoCoinRepository cryptoCoinRepository;
    @Mock
    FiatRepository fiatRepository;

    @Test
    public void testAddCurrencyMethodWithCrypto() {
        CryptoCoin crypto=new CryptoCoin();
        crypto.setClassName("crypto");
        crypto.setName("Bitcoin");
        crypto.setTicker("BTC");
        CryptoCoin returnedCrypto=null;
        try {
            Currency returnedCurrency=currencyService.addCurrency(crypto);
            Assertions.assertTrue(returnedCurrency instanceof CryptoCoin);
            returnedCrypto = (CryptoCoin) returnedCurrency;
            Assertions.assertEquals("Bitcoin",returnedCrypto.getName());
            Assertions.assertEquals("BTC",returnedCrypto.getTicker());
            CryptoCoin databaseCrypto=cryptoCoinRepository.getByName("Bitcoin");
            Assertions.assertEquals("BTC",returnedCrypto.getTicker());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAddCurrencyMethodWithFiat() {

        Fiat fiat=new Fiat();
        fiat.setClassName("fiat");
        fiat.setName("United States Dollar");
        fiat.setTicker("USD");
        try {
            Currency returnedCurrency=currencyService.addCurrency(fiat);
            Assertions.assertTrue(returnedCurrency instanceof Fiat);
            Fiat returnedCrypto = (Fiat) returnedCurrency;
            Assertions.assertEquals("United States Dollar",returnedCrypto.getName());
            Assertions.assertEquals("USD",returnedCrypto.getTicker());
            Fiat databaseFiat=fiatRepository.getByName("United States Dollar");
            Assertions.assertEquals("USD",returnedCrypto.getTicker());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

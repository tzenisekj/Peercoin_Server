package com.peercoin.web.services.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;
import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.services.helpers.CryptoCoinGenerator;
import com.peercoin.web.services.helpers.FiatGenerator;
import com.peercoin.web.services.implementations.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CurrencyServiceTest extends BaseCurrencyServiceTest<CurrencyService, Currency> {

    @Test
    public void testAddCurrencyCrypto() {
        defaultCurrency = CryptoCoinGenerator.getInstance().generateOne();
        try {
            addCurrency();
        } catch(CurrencyTickerExistsException | CurrencyNameExistsException e) {
            Assertions.fail("Error creating currency\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testAddCurrencyFiat() {
        defaultCurrency = FiatGenerator.getInstance().generateOne();
        try {
            addCurrency();
        } catch(CurrencyTickerExistsException | CurrencyNameExistsException e) {
            Assertions.fail("Error creating currency\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}

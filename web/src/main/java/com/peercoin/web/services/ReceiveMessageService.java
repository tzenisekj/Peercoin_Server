package com.peercoin.web.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyNameExistsException;
import com.peercoin.core.currency.exceptions.CurrencyTickerExistsException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameExistsException;
import com.peercoin.core.currency.services.ICurrencyService;
import com.peercoin.core.messaging.Queues;
import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.models.Fiat;
import com.peercoin.web.models.FiatMethod;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ReceiveMessageService {
    Logger logger= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    ICurrencyService currencyService;

    @Autowired
    IFiatMethodService fiatMethodService;

    @RabbitListener(queues = {Queues.CRYPTO_QUEUE,Queues.FIAT_QUEUE})
    public Currency receiveCurrencyMessage(String message) {
        try {
                Currency currency = Currency.parseJson(message);
                if (currency.getClassName().equalsIgnoreCase("crypto")) {
                    CryptoCoin cryptoCoin = new CryptoCoin();
                    cryptoCoin.setName(currency.getName());
                    cryptoCoin.setTicker(currency.getTicker());
                    return currencyService.addCurrency(cryptoCoin);
                } else if (currency.getClassName().equalsIgnoreCase("fiat")) {
                    Fiat fiat = new Fiat();
                    fiat.setName(currency.getName());
                    fiat.setTicker(currency.getTicker());
                    return currencyService.addCurrency(fiat);
                }
        } catch(CurrencyNameExistsException | CurrencyTickerExistsException e) {
            logger.log(Level.INFO,e.getMessage());
        } catch (IOException e) {
            logger.log(Level.WARNING,e.getMessage());
        }
        return null;
    }

    @RabbitListener(queues = {Queues.FIAT_METHOD_QUEUE})
    public void receiveFiatMethod(String message){
        try {
            com.peercoin.core.paymentmethods.PaymentMethod received=FiatMethod.parseJson(message);
            FiatMethod fiatMethod=new FiatMethod();
            fiatMethod.setName(received.getName());
            fiatMethodService.addFiatMethod(fiatMethod);
        } catch (IOException | PaymentMethodNameExistsException e) {
            logger.log(Level.WARNING,e.getMessage());
        }
    }
}

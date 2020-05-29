package com.peercoin.web.messaging;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CryptocoinReceiver {
    Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void receiveMessage(String message) {
        try {
            Currency currency=CryptoCoin.parseJson(message);
            CryptoCoin cryptoCoin=new CryptoCoin();
            cryptoCoin.setName(currency.getName());
            cryptoCoin.setTicker(currency.getTicker());
            System.out.println(cryptoCoin.toString());
        } catch(IOException e) {
            logger.log(Level.WARNING,e.getMessage());
        }
    }
}

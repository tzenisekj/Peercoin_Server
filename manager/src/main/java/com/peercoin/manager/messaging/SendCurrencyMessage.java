package com.peercoin.manager.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.messaging.Exchanges;
import com.peercoin.core.messaging.RoutingKeys;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SendCurrencyMessage implements ISendCurrencyMessage {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void sendMessage(Currency currency) {
        try {
            rabbitTemplate.convertAndSend(Exchanges.PEERCOIN, RoutingKeys.CRYPTO_KEY, currency.toJson());
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO,e.getMessage());
        }
    }
}

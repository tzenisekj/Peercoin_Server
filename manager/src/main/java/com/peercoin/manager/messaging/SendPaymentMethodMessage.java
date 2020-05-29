package com.peercoin.manager.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peercoin.core.messaging.Exchanges;
import com.peercoin.core.messaging.RoutingKeys;
import com.peercoin.core.paymentmethods.PaymentMethod;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SendPaymentMethodMessage implements ISendPaymentMethodMessage {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(PaymentMethod paymentMethod) {
        try {
            rabbitTemplate.convertAndSend(Exchanges.PEERCOIN,RoutingKeys.FIAT_METHOD_KEY, paymentMethod.toJson());
        } catch (JsonProcessingException e) {
            logger.log(Level.INFO,e.getMessage());
        }
    }
}

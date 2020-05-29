package com.peercoin.manager.messaging;

import com.peercoin.core.paymentmethods.PaymentMethod;

public interface ISendPaymentMethodMessage {
    void sendMessage(PaymentMethod paymentMethod);
}

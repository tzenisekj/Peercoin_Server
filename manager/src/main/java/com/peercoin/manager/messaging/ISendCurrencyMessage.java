package com.peercoin.manager.messaging;

import com.peercoin.core.currency.Currency;

public interface ISendCurrencyMessage {
    void sendMessage(Currency currency);
}

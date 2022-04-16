package com.peercoin.web.services;

import com.peercoin.web.models.Offer;
import com.peercoin.web.pojos.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getMessageHistory(Offer offer);
    void sendMessage(Offer offer, Message message);
}

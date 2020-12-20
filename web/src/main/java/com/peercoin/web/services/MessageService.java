package com.peercoin.web.services;

import com.peercoin.web.models.Offer;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService implements IMessageService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<Message> getMessageHistory(Offer offer) {
        return offer.getConversation();
    }

    @Override
    public void sendMessage(Offer offer, Message message) {
        offer.addMessage(message);
        offerRepository.save(offer);
    }
}

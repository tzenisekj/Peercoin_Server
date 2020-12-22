package com.peercoin.web.services;

import com.peercoin.web.models.Offer;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class MessageService implements IMessageService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private INotificationService notificationService;

    @Override
    public List<Message> getMessageHistory(Offer offer) {
        return offer.getConversation();
    }

    @Override
    public void sendMessage(Offer offer, Message message) {
        offer.addMessage(message);
        offerRepository.save(offer);
        if(offer.getBuyer().getId().equals(message.getSender().getId())){
            notificationService.addNotification(offer.getSeller(), message);
        }
        else{
            notificationService.addNotification(offer.getBuyer(), message);
        }
    }
}

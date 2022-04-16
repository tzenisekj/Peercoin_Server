package com.peercoin.web.services.implementations;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.User;
import com.peercoin.web.pojos.Message;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.services.IMessageService;
import com.peercoin.web.services.INotificationService;
import com.peercoin.web.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Message> getMessageHistory(Offer offer) {
        return offer.getConversation();
    }

    @Override
    public void sendMessage(Offer offer, Message message) {
        User seller = userRepository.findById(offer.getSeller()).get();
        User buyer = userRepository.findById(offer.getSeller()).get();
        offer.addMessage(message);
        offerRepository.save(offer);
        if(offer.getBuyer().equals(message.getSender().getId())){
            notificationService.addNotification(seller, message);
        }
        else{
            notificationService.addNotification(buyer, message);
        }
    }
}

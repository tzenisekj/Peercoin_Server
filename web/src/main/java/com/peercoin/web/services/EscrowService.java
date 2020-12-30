package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.InsufficientFundsException;
import com.peercoin.core.currency.exceptions.InvalidStateException;
import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.notifications.OfferNotification;
import com.peercoin.web.models.notifications.OfferNotificationType;
import com.peercoin.web.pojos.WalletContents;
import com.peercoin.web.repositories.OfferRepository;
import com.peercoin.web.repositories.OrderRepository;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class EscrowService implements IEscrowService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public boolean escrow(Offer offer) throws InsufficientFundsException, InvalidStateException {
        if (offer.isEscrowed()) {
            throw new InvalidStateException("Escrow already initiated");
        }
        User seller = userRepository.findById(offer.getSeller()).get();
        User buyer = userRepository.findById(offer.getBuyer()).get();
        Map<String, WalletContents> wallet = seller.getWallet();
        WalletContents walletContents = wallet.get(offer.getOrder().getCrypto().getName());
        walletContents.value = walletContents.value - offer.getAmount();
        if (walletContents.value < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        } else {
            seller.replaceWalletItem(offer.getOrder().getCrypto().getName(), walletContents);
            userRepository.save(seller);
        }
        offer.setEscrowed(true);
        offerRepository.save(offer);
        notificationService.addNotification(buyer, new OfferNotification(offer, OfferNotificationType.ESCROW, buyer));
        return true;
    }

    @Override
    public boolean paymentSent(Offer offer) throws InvalidStateException {
        if (offer.isSent()) {
            throw new InvalidStateException("Payment already marked as sent");
        }
        User seller = userRepository.findById(offer.getSeller()).get();
        offer.setSent(true);
        offerRepository.save(offer);
        notificationService.addNotification(seller, new OfferNotification(offer, OfferNotificationType.SENT, seller));
        return true;
    }

    @Override
    public boolean paymentReceived(Offer offer) throws InvalidStateException {
        if (offer.isReceived()) {
            throw new InvalidStateException("Payment already marked as received");
        }
        User buyer = userRepository.findById(offer.getBuyer()).get();
        Map<String, WalletContents> wallet = buyer.getWallet();
        WalletContents walletContents = wallet.get(offer.getOrder().getCrypto().getName());
        walletContents.value += offer.getAmount();
        offer.setReceived(true);
        offer.setCompleted(true);
        if (offer.getOrder().isRemoveOnOfferCompletion()) {
            Order order = orderRepository.getById(offer.getOrder().getId());
            order.setActive(false);
            orderRepository.save(order);
        }
        offerRepository.save(offer);
        notificationService.addNotification(buyer, new OfferNotification(offer, OfferNotificationType.RECEIVED, buyer));
        return true;
    }

    @Override
    public void openInvestigation(Offer offer) throws InvalidStateException {
        if (offer.isOpenInvestigation()) {
            throw new InvalidStateException("Investigation already opened");
        }
        //TODO: implement logic to notify admins
        offer.setOpenInvestigation(true);
        User seller = userRepository.findById(offer.getSeller()).get();
        User buyer = userRepository.findById(offer.getBuyer()).get();
        offerRepository.save(offer);
        notificationService.addNotification(buyer, new OfferNotification(offer, OfferNotificationType.INVESTIGATION, seller));
        notificationService.addNotification(seller, new OfferNotification(offer, OfferNotificationType.INVESTIGATION, buyer));
    }
}

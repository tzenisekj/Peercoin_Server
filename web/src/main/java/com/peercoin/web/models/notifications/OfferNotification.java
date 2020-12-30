package com.peercoin.web.models.notifications;

import com.peercoin.web.models.Notifiable;
import com.peercoin.web.models.Offer;
import com.peercoin.web.models.User;
import org.springframework.stereotype.Component;

public class OfferNotification implements Notifiable {
    private String message;

    public OfferNotification() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OfferNotification(Offer offer, OfferNotificationType offerNotificationType, User notifying) {
        switch (offerNotificationType) {
            case ESCROW:
                message = "Escrow has been instantiated for order of " + offer.getAmount() + " " + offer.getOrder().getCrypto().getTicker();
                break;
            case SENT:
                message = notifying.getUsername() + " has marked order of " + offer.getAmount() + " " + offer.getOrder().getCrypto().getTicker() + " as sent";
                break;
            case RECEIVED:
                message = notifying.getUsername() + " has marked order of " + offer.getAmount() + " " + offer.getOrder().getCrypto().getTicker() + " as received." + offer.getOrder().getCrypto().getName() + " added to your account";
                break;
            case INVESTIGATION:
                message = "Admin action initiated for oder of " + offer.getAmount() + " " + offer.getOrder().getCrypto().getTicker();
                break;
            default:
                message = "Something happened on order of " + offer.getAmount() + " " + offer.getOrder().getCrypto().getTicker();
        }
    }

    @Override
    public String notificationMessage() {
        return message;
    }
}

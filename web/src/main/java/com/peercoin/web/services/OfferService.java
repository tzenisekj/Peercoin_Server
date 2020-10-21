package com.peercoin.web.services;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.OfferDto;
import com.peercoin.web.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfferService implements IOfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Offer submitOffer(OfferDto offerDto, Order order, User buyer) {
        Offer offer=new Offer();
        offer.setAmount(retrieveOfferAmount(offerDto,order.getExchangeRate()));
        offer.setOrder(order);
        offer.setBuyer(buyer);
        offerRepository.save(offer);
        return offer;
    }

    private double retrieveOfferAmount(OfferDto offerDto, double exchangeRate) {
        if (offerDto.getType().equalsIgnoreCase("crypto")){
            return offerDto.getAmount()*exchangeRate;
        } else { //if offerDto.getType().equalsIgnoreCase("payment"))
            return offerDto.getAmount();
        }
    }
}

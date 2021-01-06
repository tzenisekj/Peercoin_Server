package com.peercoin.web.services;

import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.OfferDto;


public interface IOfferService {
    Offer submitOffer(OfferDto offerDto, Order order, User buyer);
}

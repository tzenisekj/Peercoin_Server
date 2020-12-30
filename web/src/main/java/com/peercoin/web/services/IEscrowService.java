package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.InsufficientFundsException;
import com.peercoin.core.currency.exceptions.InvalidStateException;
import com.peercoin.web.models.Offer;

public interface IEscrowService {
    boolean escrow(Offer offer) throws InsufficientFundsException, InvalidStateException;
    boolean paymentSent(Offer offer) throws InvalidStateException;
    boolean paymentReceived(Offer offer) throws InvalidStateException;
    void openInvestigation(Offer offer) throws InvalidStateException;
}

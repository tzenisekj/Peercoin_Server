package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.PaymentMethodNameExistsException;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.web.models.FiatMethod;

import java.util.List;

public interface IFiatMethodService {
    FiatMethod addFiatMethod(FiatMethod fiatMethod) throws PaymentMethodNameExistsException;
    List<PaymentMethod> getAllPaymentMethods();
    PaymentMethod findByName(String name);
}

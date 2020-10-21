package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.PaymentMethodNameExistsException;
import com.peercoin.web.models.FiatMethod;

public interface IFiatMethodService {
    FiatMethod addFiatMethod(FiatMethod fiatMethod) throws PaymentMethodNameExistsException;
}

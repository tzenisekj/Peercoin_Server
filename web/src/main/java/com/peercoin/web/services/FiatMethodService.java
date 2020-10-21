package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.PaymentMethodNameExistsException;
import com.peercoin.web.models.FiatMethod;
import com.peercoin.web.repositories.FiatMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiatMethodService implements IFiatMethodService {
    @Autowired
    FiatMethodRepository fiatMethodRepository;

    @Override
    public FiatMethod addFiatMethod(FiatMethod fiatMethod) throws PaymentMethodNameExistsException {
        checkFiatMethodExistence(fiatMethod);
        return fiatMethodRepository.save(fiatMethod);
    }

    public void checkFiatMethodExistence(FiatMethod fiatMethod) throws PaymentMethodNameExistsException {
        if (fiatMethodRepository.findByName(fiatMethod.getName()) != null) {
            throw new PaymentMethodNameExistsException("payment method name " + fiatMethod.getName() + " already exists");
        }
    }
}

package com.peercoin.web.services.implementations;

import com.peercoin.core.currency.exceptions.PaymentMethodNameExistsException;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.web.houseKeepers.NonPersistentRepositories;
import com.peercoin.web.models.FiatMethod;
import com.peercoin.web.repositories.FiatMethodRepository;
import com.peercoin.web.services.IFiatMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class FiatMethodService implements IFiatMethodService {
    @Autowired
    FiatMethodRepository fiatMethodRepository;

    @Override
    public FiatMethod addFiatMethod(FiatMethod fiatMethod) throws PaymentMethodNameExistsException {
        checkFiatMethodExistence(fiatMethod);
        return fiatMethodRepository.save(fiatMethod);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        NonPersistentRepositories nprs = NonPersistentRepositories.getInstance();
        Iterable<PaymentMethod> paymentIterables = nprs.paymentMethodRepository.findAll();
        List<FiatMethod> fiatMethods = fiatMethodRepository.findAll();
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentIterables.forEach(paymentMethods::add);
        fiatMethods.forEach(f -> {
            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setName(f.getName());
            paymentMethods.add(paymentMethod);
        });
        return paymentMethods;
    }

    @Override
    public PaymentMethod findByName(String name) {
        NonPersistentRepositories nprs = NonPersistentRepositories.getInstance();
        Iterable<PaymentMethod> paymentIterables = nprs.paymentMethodRepository.findAll();
        List<FiatMethod> fiatMethods = fiatMethodRepository.findAll();
        for (FiatMethod fiatMethod : fiatMethods) {
            if (fiatMethod.getName().equalsIgnoreCase(name)) {
                return fiatMethod;
            }
        }
        for (PaymentMethod paymentMethod : paymentIterables) {
            if (paymentMethod.getName().equalsIgnoreCase(name)) {
                return paymentMethod;
            }
        }
        return null;
    }

    public void checkFiatMethodExistence(FiatMethod fiatMethod) throws PaymentMethodNameExistsException {
        if (fiatMethodRepository.findByName(fiatMethod.getName()) != null) {
            throw new PaymentMethodNameExistsException("payment method name " + fiatMethod.getName() + " already exists");
        }
    }
}

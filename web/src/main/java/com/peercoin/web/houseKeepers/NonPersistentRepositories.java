package com.peercoin.web.houseKeepers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.web.repositories.NonPersistentRepository;
import com.peercoin.web.repositories.NonPersistentRepositoryImpl;

public class NonPersistentRepositories {
    public NonPersistentRepository<CryptoCoin> cryptoRepository;
    public NonPersistentRepository<Fiat> fiatRepository;
    public NonPersistentRepository<PaymentMethod> paymentMethodRepository;

    protected static NonPersistentRepositories instance = null;

    protected NonPersistentRepositories() {
        cryptoRepository = new NonPersistentRepositoryImpl<>();
        fiatRepository = new NonPersistentRepositoryImpl<>();
        paymentMethodRepository = new NonPersistentRepositoryImpl<>();
    }

    public static NonPersistentRepositories getInstance() {
        if (instance == null) {
            instance = new NonPersistentRepositories();
        }
        return instance;
    }

}

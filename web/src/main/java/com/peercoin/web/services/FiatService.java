package com.peercoin.web.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.web.houseKeepers.NonPersistentRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FiatService extends CurrencyService implements IFiatService{
    @Override
    public List<Fiat> getAllFiat() {
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        List<com.peercoin.web.models.Fiat> stored = super.fiatRepository.findAll();
        Iterable<Fiat> fiatIterable = repositories.fiatRepository.findAll();
        List<Fiat> fiats = new ArrayList<>();
        fiatIterable.forEach(fiats::add);
        fiats.addAll(stored);
        return fiats;
    }

    @Override
    public Currency getByName(String name) {
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        List<com.peercoin.web.models.Fiat> stored = super.fiatRepository.findAll();
        Iterable<Fiat> fiatIterable = repositories.fiatRepository.findAll();
        for (com.peercoin.web.models.Fiat fiat : stored) {
            if (fiat.getName().equalsIgnoreCase(name)) {
                return fiat;
            }
        }
        for (Fiat fiat : fiatIterable) {
            if (fiat.getName().equalsIgnoreCase(name)) {
                return fiat;
            }
        }
        return null;
    }
}

package com.peercoin.web.repositories;

import com.peercoin.core.PaymentEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class NonPersistentRepositoryImpl<T extends PaymentEntity> implements NonPersistentRepository<T> {
    List<T> items;

    public NonPersistentRepositoryImpl() {
        items = new ArrayList<>();
    }

    @Override
    public Iterable<T> findAll() {
        return items;
    }

    @Override
    public long count() {
        return items.size();
    }

    @Override
    public boolean exists(T example) {
        AtomicBoolean result = new AtomicBoolean(false);
        items.forEach(i -> {
            if (example.equals(i)) {
                result.set(true);
            }
        });
        return result.get();
    }

    @Override
    public boolean insert(T entity) {
        items.add(entity);
        return true;
    }

    @Override
    public boolean insert(Iterable<T> entities) {
        entities.forEach(e -> {
            items.add(e);
        });
        return true;
    }

    @Override
    public Optional<T> findByName(String name) {
        AtomicReference<T> itemToReturn = null;
        AtomicBoolean exists = new AtomicBoolean(false);
        items.forEach(i -> {
            if (name.equals(i.getName())) {
                exists.set(true);
                itemToReturn.set(i);
            }
        });
        if (exists.get()) {
            return Optional.of(itemToReturn.get());
        }
        return Optional.empty();
    }
}

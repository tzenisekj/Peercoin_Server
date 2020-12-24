package com.peercoin.web.repositories;

import com.peercoin.core.PaymentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface NonPersistentRepository<T extends PaymentEntity > {
    long count();

    boolean exists(T example);

    boolean insert(T entity);

    boolean insert(Iterable<T> entities);

    public Optional<T> findByName(String name);

    public Iterable<T> findAll();
}

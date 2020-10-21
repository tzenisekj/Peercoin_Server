package com.peercoin.web.repositories;

import com.peercoin.web.models.Fiat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FiatRepository extends MongoRepository<Fiat,String> {
    Fiat getByName(@Param("name") String name);
    Fiat getByTicker(@Param("ticker") String ticker);
}

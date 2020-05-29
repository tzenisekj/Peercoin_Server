package com.peercoin.web.repositories;

import com.peercoin.web.models.CryptoCoin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinRepository extends MongoRepository<CryptoCoin,String> {
    CryptoCoin getByName(@Param("name") String name);
    CryptoCoin getByTicker(@Param("ticker") String ticker);
}

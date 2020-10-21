package com.peercoin.web.repositories;

import com.peercoin.web.models.FiatMethod;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FiatMethodRepository extends MongoRepository<FiatMethod,String> {
    FiatMethod findByName(@Param("name") String name);
}

package com.peercoin.web.repositories;

import com.peercoin.web.models.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends MongoRepository<Offer,String> {
    List<Offer> getByBuyer(@Param("buyer") User buyer);
    List<Offer> getBySeller(@Param("seller") User seller);
}

package com.peercoin.web.repositories;

import com.peercoin.web.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> getByInitiator(@Param("initiator") String initiator);
}

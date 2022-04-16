package com.peercoin.web.repositories;

import com.peercoin.web.models.HelpTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface HelpTicketRepository extends MongoRepository<HelpTicket, String> {
    List<HelpTicket> getByUserId(@Param("userId") String userId);
}

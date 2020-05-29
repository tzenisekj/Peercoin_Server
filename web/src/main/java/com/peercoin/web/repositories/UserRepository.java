package com.peercoin.web.repositories;

import com.peercoin.web.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User getByUsername(@Param("username") String username);
}

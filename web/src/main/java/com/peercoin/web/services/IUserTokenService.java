package com.peercoin.web.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserTokenService {
    Optional<UserDetails> findByToken(String token);
}

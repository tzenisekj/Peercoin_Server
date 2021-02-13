package com.peercoin.web.services.implementations;

import com.peercoin.web.models.User;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IUserTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserTokenService implements IUserTokenService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDetails> findByToken(String token) {
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent()) {
            User u = user.get();
            if (u.getExpiration().after(new Date())) {

                return Optional.of(new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), true, true, true, true, retrieveAuthorities(u.getRoles())));
            }
        }
        return Optional.empty();
    }

    private static List<GrantedAuthority> retrieveAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities=new ArrayList<>();
        for (String role:roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}

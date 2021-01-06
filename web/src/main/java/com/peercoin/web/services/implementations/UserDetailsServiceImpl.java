package com.peercoin.web.services.implementations;

import com.peercoin.web.models.User;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.getByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        boolean enabled=true;
        boolean accountNotExpired=true;
        boolean credentialsNotExpired=true;
        boolean accountNotLocked=true;
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                enabled,
                accountNotExpired,
                credentialsNotExpired,
                accountNotLocked,
                retrieveAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> retrieveAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities=new ArrayList<>();
        for (String role:roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}

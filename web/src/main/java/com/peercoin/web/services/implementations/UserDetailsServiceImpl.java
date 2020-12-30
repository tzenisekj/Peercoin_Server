package com.peercoin.web.services.implementations;

import com.peercoin.web.models.User;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.getByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        try {
            userService.setApiKey(user);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "Error with encryption algorithm:\n" + e.getMessage());
            e.printStackTrace();
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

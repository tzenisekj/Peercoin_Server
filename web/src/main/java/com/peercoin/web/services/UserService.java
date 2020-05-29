package com.peercoin.web.services;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(UserDto userDto) throws UsernameExistsException {
        if (userRepository.getByUsername(userDto.getUsername()) != null) {
            throw new UsernameExistsException("username " + userDto.getUsername() + " already exists");
        }
        User user=new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ROLE_user");
        userRepository.save(user);
        return user;

    }
}

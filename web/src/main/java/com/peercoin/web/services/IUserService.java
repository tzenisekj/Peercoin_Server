package com.peercoin.web.services;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UserDto;

import java.security.NoSuchAlgorithmException;

public interface IUserService {
    User registerNewUser(UserDto userDto) throws UsernameExistsException;
    User getUserByApiKey(String apiKey);
    void setApiKey(User user) throws NoSuchAlgorithmException;

}

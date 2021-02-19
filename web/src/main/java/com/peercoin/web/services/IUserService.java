package com.peercoin.web.services;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UpdatePasswordDto;
import com.peercoin.web.models.dtos.UserDto;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface IUserService {
    boolean withdraw(User user, String name, String address, double amount);
    boolean updatePassword(UpdatePasswordDto updatePasswordDto);
    boolean updateUser(User user);
    boolean deleteUser(String id);
    User registerAdmin(String username, String password) throws UsernameExistsException;
    User login(String username, String password);
    Optional<User> findById(String id);
    User registerNewUser(UserDto userDto) throws UsernameExistsException;
    boolean logout(User user);
}

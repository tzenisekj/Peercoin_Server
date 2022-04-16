package com.peercoin.web.controllers;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.models.dtos.UserLoginDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.responses.FailureResponses;
import com.peercoin.web.responses.SuccessResponses;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
@Validated
public class UserController {
    private Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto) {
        return registerUser(userDto);
    }

    private ResponseEntity<String> registerUser(UserDto userDto) {
        try {
            userService.registerNewUser(userDto);
        } catch (UsernameExistsException e) {
            logger.log(Level.FINE, e.getMessage());
            logger.log(Level.INFO,"Attempt to register invalid username " + userDto.getUsername());
            return FailureResponses.failure(e.getMessage());
        }
        return SuccessResponses.success("successfully registered user");
    }

    @PostMapping(path = "/token", produces = "application/json")
    public ResponseEntity<User> getToken(@RequestBody UserLoginDto userLoginDto){
        User user= userService.login(userLoginDto.getUsername(),userLoginDto.getPassword());
        if(user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (Authentication authentication) {
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        if (userService.logout(user)) {
            return SuccessResponses.success("Logged out successfully");
        }
        return FailureResponses.failure("Failed to log out");
    }

    @GetMapping(value = "/api/users/user/{id}",produces = "application/json")
    public User getUserDetail(@PathVariable String id){
        return userService.findById(id).orElse(null);
    }

    @GetMapping(value = "/api/user", produces = "application/json")
    public User getUser(Authentication authentication) {
        return userRepository.getByUsername(((UserDetails)authentication.getPrincipal()).getUsername());
    }
}

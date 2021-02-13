package com.peercoin.web.controllers;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UpdatePasswordDto;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.responses.FailureResponses;
import com.peercoin.web.responses.SuccessResponses;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/admin/user")
public class AdminUserController {
    private static final Logger logger = Logger.getLogger(AdminUserController.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDto userDto) {
        boolean res;
        try {
            userService.registerNewUser(userDto);
            res = true;
        } catch (UsernameExistsException e) {
            res = false;
        }
        return resolveResponse(res, userDto.getUsername(),"adding");
    }

    @DeleteMapping(produces = "application/json")
    public ResponseEntity<String> deleteUser(@RequestParam("id") String id) {
        boolean success = userService.deleteUser(id);
        return resolveResponse(success, id, "deleting");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        return resolveResponse(success, user.getUsername(), "updating");
    }

    @PutMapping(path = "/passwordreset", produces = "application/json")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        boolean success = userService.updatePassword(updatePasswordDto);
        return resolveResponse(success, updatePasswordDto.getId(), "updating password");
    }

    private ResponseEntity<String> resolveResponse(boolean success, String identifier, String method) {
        String message;
        if (success) {
            message = "Success " + method + " user " + identifier;
            logger.log(Level.FINE, message);
            return SuccessResponses.success(message);
        }
        message = "Failure " + method + " user " + identifier;
        logger.log(Level.INFO, message);
        return FailureResponses.failure(message);
    }


    public static class UserIdDto {
        public String id;
        public UserIdDto(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

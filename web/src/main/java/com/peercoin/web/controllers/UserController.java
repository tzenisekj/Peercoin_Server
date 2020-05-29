package com.peercoin.web.controllers;

import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@SuppressWarnings("unused")
@Validated
public class UserController {
    private Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new UserDto());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserDto userDto) {
        return registerUser(userDto);
    }

    private String registerUser(UserDto userDto) {
        try {
            userService.registerNewUser(userDto);
        } catch (UsernameExistsException e) {
            logger.log(Level.FINE, e.getMessage());
            logger.log(Level.INFO,"Attempt to register invalid username " + userDto.getUsername());
            return "redirect:/register/error=usernameexists";
        }
        return "redirect:/";
    }
}

package com.peercoin.web.controllers;

import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.WithdrawDto;
import com.peercoin.web.pojos.WalletContents;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public Map<String, WalletContents> getWalletView(Authentication authentication) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userRepository.getByUsername(userDetails.getUsername());
        return user.getWallet();
    }

    @PostMapping("/withdraw/{name}")
    public boolean withdraw(@PathVariable("name") String name, @RequestBody WithdrawDto withdrawDto, Authentication authentication) {
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        if (user != null) {
            return userService.withdraw(user, name, withdrawDto.getAddress(), withdrawDto.getAmount());
        }
        return false;
    }

}

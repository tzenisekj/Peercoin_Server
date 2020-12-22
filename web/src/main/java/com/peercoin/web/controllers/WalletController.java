package com.peercoin.web.controllers;

import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.models.User;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    @GetMapping
    public String getWalletView(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userRepository.getByUsername(userDetails.getUsername());
        model.addAttribute("wallet", user.getWallet());
        List<CryptoCoin> cryptos=cryptoCoinRepository.findAll();
        cryptos.forEach(crypto -> {
            if (!user.getWallet().containsKey(crypto)) {
                user.insertWalletItem(crypto.getName(),0.0);
            }
        });
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("cryptos", cryptoCoinRepository.findAll());
        return "wallet";
    }
}

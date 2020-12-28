package com.peercoin.web.controllers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.web.models.User;
import com.peercoin.web.pojos.WalletContents;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.ICryptoCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

    @GetMapping
    public String getWalletView(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userRepository.getByUsername(userDetails.getUsername());
        List<CryptoCoin> cryptos = cryptoCoinService.getAllCryptoCoins();
        model.addAttribute("wallet", user.getWallet());
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("cryptos", cryptos);
        return "wallet";
    }
}

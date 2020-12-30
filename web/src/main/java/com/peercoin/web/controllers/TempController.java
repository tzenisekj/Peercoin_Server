package com.peercoin.web.controllers;

import com.peercoin.web.models.User;
import com.peercoin.web.models.temp.CreditAccountDto;
import com.peercoin.web.pojos.WalletContents;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tmp")
public class TempController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/credit-account")
    public ResponseEntity creditAccount(@RequestBody CreditAccountDto creditAccountDto) {
        User user = userRepository.getByUsername(creditAccountDto.username);
        WalletContents walletContents = user.getWallet().get(creditAccountDto.crypto);
        walletContents.value += creditAccountDto.amount;
        user.replaceWalletItem(creditAccountDto.crypto, walletContents);
        userRepository.save(user);
        return ResponseEntity.ok("Ok");
    }
}

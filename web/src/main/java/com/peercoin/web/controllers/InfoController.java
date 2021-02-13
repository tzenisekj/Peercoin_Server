package com.peercoin.web.controllers;

import com.peercoin.web.pojos.Info;
import com.peercoin.web.services.ICryptoCoinService;
import com.peercoin.web.services.IFiatMethodService;
import com.peercoin.web.services.IFiatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class InfoController {
    @Autowired
    private ICryptoCoinService cryptoCoinService;
    @Autowired
    private IFiatService fiatService;
    @Autowired
    private IFiatMethodService fiatMethodService;

    @GetMapping("/info")
    public Info getInfo() {
        return new Info(cryptoCoinService.getAllCryptoCoins(), fiatService.getAllFiat(),fiatMethodService.getAllPaymentMethods());
    }
}

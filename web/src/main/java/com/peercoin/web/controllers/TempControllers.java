package com.peercoin.web.controllers;

import com.peercoin.core.currency.services.ICurrencyService;
import com.peercoin.web.models.CryptoCoin;
import com.peercoin.web.models.Fiat;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.FiatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TempControllers {
    @Autowired
    ICurrencyService currencyService;

    @Autowired
    CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    FiatRepository fiatRepository;

    @GetMapping("/crypto")
    public List<CryptoCoin> getCryptos() {
        return cryptoCoinRepository.findAll();
    }
    @GetMapping("/fiat")
    public List<Fiat> getFiat() {
        return fiatRepository.findAll();
    }
    @PostMapping("/crypto")
    public ResponseEntity addCrypto(@RequestBody CryptoCoin cryptoCoin){
        CryptoCoin crypto;
        try {
            crypto=(CryptoCoin) currencyService.addCurrency(cryptoCoin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("");
        }
        return ResponseEntity.ok(crypto);
    }
    @PostMapping("/fiat")
    public ResponseEntity addFiat(@RequestBody Fiat potential) {
        Fiat fiat;
        try {
            fiat=(Fiat) currencyService.addCurrency(potential);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body("");
        }
        return ResponseEntity.ok(fiat);
    }
}

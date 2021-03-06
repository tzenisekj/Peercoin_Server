package com.peercoin.web.models;

import com.peercoin.core.currency.CurrencyMethods;
import com.peercoin.web.pojos.WalletContents;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document
public class User {
    @Id
    private String id;

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Map<String, WalletContents> wallet;

    private List<Notifiable> storedNotifiables;

    private List<String> roles;
    private String token;
    private Date expiration;

    public User() {
        roles=new ArrayList<>();
        wallet=new HashMap<>();
        storedNotifiables = new ArrayList<>();
    }

    public List<Notifiable> getStoredNotifiables(){ return storedNotifiables; }

    public void addNotification(Notifiable newNotification){ storedNotifiables.add(newNotification); }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRole(String role) {
        roles.add(role);
    }

    public Map<String, WalletContents> getWallet() {
        return wallet;
    }

    public void insertWalletItem(String cryptoCoin, double value, CurrencyMethods currencyMethods) {
        WalletContents walletContents = new WalletContents(value, currencyMethods.createAddress());
        this.wallet.put(cryptoCoin, walletContents);
    }

    public void replaceWalletItem(String cryptoCoin, WalletContents walletContents) {
        this.wallet.put(cryptoCoin, walletContents);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}

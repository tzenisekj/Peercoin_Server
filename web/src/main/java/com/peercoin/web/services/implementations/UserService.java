package com.peercoin.web.services.implementations;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UpdatePasswordDto;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.ICryptoCoinService;
import com.peercoin.web.services.IHelpTicketService;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@SuppressWarnings("unused")
public class UserService implements IUserService {
    Logger logger = Logger.getLogger(UserService.class.getName());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

    @Autowired
    private IHelpTicketService helpTicketService;

    @Override
    public User login(String username, String password) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return null;
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.HOUR, 6);
            user.setExpiration(c.getTime());
            userRepository.save(user);
            return user;
        }
        return null;

    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public boolean deleteUser(String id) {
        User user = findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        User updater = findById(user.getId()).orElse(null);
        if (updater != null) {
            updater.setUsername(user.getUsername());
            updater.setEmail(user.getEmail());
            updater.setPhoneNumber(user.getPhoneNumber());
            userRepository.save(updater);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(User user, String name, String address, double amount) {
        Currency cryptoCoin = cryptoCoinService.getByName(name);
        boolean res = false;
        double value = user.getWallet().get(cryptoCoin.getName()).value - amount;

        if (cryptoCoin instanceof CryptoCoin) {
            if (value > 0) {
                try {
                    res = cryptoCoin.getCurrencyMethods().pay(address, amount);
                    if (res) {
                        user.insertWalletItem(cryptoCoin.getName(), value, cryptoCoin.getCurrencyMethods());
                    }
                    userRepository.save(user);
                    return res;
                } catch (Exception e) {
                    String message = "Administration wallet for crypto " + cryptoCoin.getName() + " likely has no funds, or is not connected correctly";
                    logger.log(Level.SEVERE, message);
                    helpTicketService.raise(user, "AUTOMATICALLY GENERATED - " + message);
                }
            }
        }

        return false;
    }

    @Override
    public boolean updatePassword(UpdatePasswordDto updatePasswordDto) {
        User user = findById(updatePasswordDto.getId()).orElse(null);
        if (user == null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User registerNewUser(UserDto userDto) throws UsernameExistsException {
        if (userRepository.getByUsername(userDto.getUsername()) != null) {
            throw new UsernameExistsException("username " + userDto.getUsername() + " already exists");
        }
        User user=new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ROLE_user");

        populateCryptos(user);

        userRepository.save(user);
        return user;

    }

    @Override
    public boolean logout(User user) {
        user.setExpiration(new Date());
        userRepository.save(user);
        return true;
    }

    @Override
    public User registerAdmin(String username, String password) throws UsernameExistsException {
        if (userRepository.getByUsername(username) != null) {
            throw new UsernameExistsException("username " + username + " already exists");
        }
        User user=new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ROLE_admin");
        userRepository.save(user);
        return user;
    }

    public void populateCryptos(User user) {
        List<CryptoCoin> cryptos=cryptoCoinService.getAllCryptoCoins();
        for (CryptoCoin crypto : cryptos) {
            user.insertWalletItem(crypto.getName(), 0.0, crypto.getCurrencyMethods());
        }
    }
}

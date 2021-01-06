package com.peercoin.web.services.implementations;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.ICryptoCoinService;
import com.peercoin.web.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

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

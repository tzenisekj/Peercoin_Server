package com.peercoin.web.services.implementations;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.UserDto;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.ICryptoCoinService;
import com.peercoin.web.services.IUserService;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

    private final String AES = "AES";

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

    @Override
    public User getUserByApiKey(String apiKey) {
        return userRepository.getByRestApiKey(apiKey);
    }

    private SecretKey createAESKey() throws NoSuchAlgorithmException {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);
        keygenerator.init(256, securerandom);
        SecretKey key = keygenerator.generateKey();
        return key;
    }
    @Override
    public void setApiKey(User user) throws NoSuchAlgorithmException {
        SecretKey Symmetrickey = createAESKey();
                user.setRestApiKey(Base64.encode(Symmetrickey.getEncoded()));
        userRepository.save(user);
    }

    public void populateCryptos(User user) {
        List<CryptoCoin> cryptos=cryptoCoinService.getAllCryptoCoins();
        for (CryptoCoin crypto : cryptos) {
            user.insertWalletItem(crypto.getName(), 0.0, crypto.getCurrencyMethods());
        }
    }
}

package com.peercoin.web.houseKeepers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.web.models.User;
import com.peercoin.web.pojos.WalletContents;
import com.peercoin.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class DepositHouseKeeper {
    private Logger logger = Logger.getLogger(DepositHouseKeeper.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 300000)
    public void checkAllUserAccounts() {
        List<User> users = userRepository.findAll();
        logger.log(Level.INFO, "Beginning scheduled search of active user's deposits");
        for (User user : users) {
            if (user.getExpiration().after(new Date())) {
                logger.log(Level.INFO, "Searching for deposits on user " + user.getUsername());
                Iterable<CryptoCoin> cryptoCoins = NonPersistentRepositories.getInstance().cryptoRepository.findAll();
                for (CryptoCoin crypto: cryptoCoins) {
                    if (user.getWallet().get(crypto.getName()) == null) {
                        user.insertWalletItem(crypto.getName(), 0, crypto.getCurrencyMethods());
                    }
                    float res = crypto.getCurrencyMethods().getAddressConfirmed(user.getWallet().get(crypto.getName()).address);
                    if (res < 0) {
                        WalletContents walletContents = new WalletContents();
                        walletContents.address = crypto.getCurrencyMethods().createAddress();
                        walletContents.value = res + user.getWallet().get(crypto.getName()).value;
                        user.replaceWalletItem(crypto.getName(), walletContents);
                    }
                }
            }
        }
    }
}

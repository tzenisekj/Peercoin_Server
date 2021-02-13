package com.peercoin.web.houseKeepers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.integration.CurrencyFactory;
import com.peercoin.web.exceptions.UsernameExistsException;
import com.peercoin.web.repositories.NonPersistentRepository;
import com.peercoin.web.services.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class StartupHouseKeeper {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Value("${registry.python.currencies}")
    private String currencies;

    @Value("${registry.python.methods}")
    private String methods;

    @Autowired
    private UserService userService;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        NonPersistentRepository<CryptoCoin> cryptoCoinRepository = repositories.cryptoRepository;
        NonPersistentRepository<Fiat> fiatRepository = repositories.fiatRepository;
        NonPersistentRepository<PaymentMethod> paymentMethodRepository = repositories.paymentMethodRepository;
        CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
        String[] currencies = this.currencies.split(",");
        for (String currency : currencies) {
            Currency module = currencyFactory.createCurrency(currency);
            if (module instanceof CryptoCoin) {
                cryptoCoinRepository.insert((CryptoCoin) module);
            } else if (module instanceof Fiat) {
                fiatRepository.insert((Fiat) module);
            }
        }
        String[] paymentMethods = methods.split(",");
        for (String method : paymentMethods) {
            PaymentMethod module = currencyFactory.createPaymentMethod(method);
            paymentMethodRepository.insert(module);
        }
        addAdmin();
    }

    private void addAdmin() {
        try {
            userService.registerAdmin("admin", "password");
        } catch(UsernameExistsException ignore) {

        }
    }
}

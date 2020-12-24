package com.peercoin.web.houseKeepers;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.Fiat;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.integration.CurrencyFactory;
import com.peercoin.web.repositories.NonPersistentRepository;
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
//
//    @Autowired
//    NonPersistentCryptoCoinRepository cryptoCoinRepository;
//
//    @Autowired
//    NonPersistentFiatRepository fiatRepository;
//
//    @Autowired
//    NonPersistentPaymentMethodRepository paymentMethodRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        logger.log(Level.WARNING, "hit context refresh");
        NonPersistentRepositories repositories = NonPersistentRepositories.getInstance();
        NonPersistentRepository<CryptoCoin> cryptoCoinRepository = repositories.cryptoRepository;
        NonPersistentRepository<Fiat> fiatRepository = repositories.fiatRepository;
        NonPersistentRepository<PaymentMethod> paymentMethodRepository = repositories.paymentMethodRepository;
        CurrencyFactory currencyFactory = CurrencyFactory.getInstance();
        String[] currencies = this.currencies.split(",");
        for (String currency : currencies) {
            logger.log(Level.WARNING, currency);
            Currency module = currencyFactory.createCurrency(currency);
            if (module instanceof CryptoCoin) {
                cryptoCoinRepository.insert((CryptoCoin) module);
            } else if (module instanceof Fiat) {
                fiatRepository.insert((Fiat) module);
            }
        }
        String[] paymentMethods = methods.split(",");
        for (String method : paymentMethods) {
            logger.log(Level.WARNING, method);
            PaymentMethod module = currencyFactory.createPaymentMethod(method);
            paymentMethodRepository.insert(module);
        }
        System.out.println("end context refresh event");

    }
}

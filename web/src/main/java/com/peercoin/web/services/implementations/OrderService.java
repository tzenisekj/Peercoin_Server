package com.peercoin.web.services.implementations;

import com.peercoin.core.currency.CryptoCoin;
import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameDoesNotExistException;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.web.exceptions.IdDoesNotExist;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.OrderDto;
import com.peercoin.web.repositories.*;
import com.peercoin.web.services.ICryptoCoinService;
import com.peercoin.web.services.IFiatMethodService;
import com.peercoin.web.services.IFiatService;
import com.peercoin.web.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
@SuppressWarnings("unused")
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

    @Autowired
    private IFiatService fiatService;

    @Autowired
    private IFiatMethodService fiatMethodService;

    @Override
    public Order submitOrder(OrderDto orderDto, String type, String username)
            throws CurrencyDoesNotExistException, PaymentMethodNameDoesNotExistException {
        User user=userRepository.getByUsername(username);
        CryptoCoin cryptoCoin=(CryptoCoin) cryptoCoinService.getByName(orderDto.getCrypto());
        Currency currency=fiatService.getByName(orderDto.getCurrency());
        if (currency==null){
            currency=cryptoCoinService.getByName(orderDto.getCurrency());
        }
        PaymentMethod fiatMethod=fiatMethodService.findByName(orderDto.getPaymentMethod());
        verifyCurrencyExistence(cryptoCoin,currency);
        verifyPaymentMethodExistence(fiatMethod);

        Order order = new Order();
        order.setCrypto(cryptoCoin);
        order.setPayment(currency);
        order.setOrderType(type);
        order.setInitiator(user.getId());
        order.setMethod(fiatMethod);
        order.setExchangeRate(orderDto.getExchangeRate());
        order.setMax(orderDto.getMax());
        order.setMin(orderDto.getMin());
        order.setRemoveOnOfferCompletion(orderDto.isRemoveOnOfferCompletion());
        switch(orderDto.getBuy().toLowerCase()) {
            case "buy":
                order.setBuy(true);
                break;
            case "sell":
                order.setBuy(false);
                break;
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(String id) throws IdDoesNotExist {
        return orderRepository.findById(id).orElseThrow(() -> new IdDoesNotExist("id " + id + "does not exist"));
    }

    @Override
    public boolean markInactive(String id) throws IdDoesNotExist {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (!order.isActive()) {
                return false;
            }
            order.setActive(false);
            orderRepository.save(order);
            return true;
        } else {
            throw new IdDoesNotExist("id " + id + " does not exist in OrderRepository");
        }
    }

    private void verifyCurrencyExistence(CryptoCoin cryptoCoin, Currency currency) throws CurrencyDoesNotExistException,PaymentMethodNameDoesNotExistException {
        if (cryptoCoin==null || currency == null) {
            throw new CurrencyDoesNotExistException("attempted to use non-existent currency");
        }
    }

    private void verifyPaymentMethodExistence(PaymentMethod paymentMethod) throws PaymentMethodNameDoesNotExistException {
        if (paymentMethod == null) {
            throw new PaymentMethodNameDoesNotExistException("attempted to use non-existent payment method");
        }
    }
}

package com.peercoin.web.services;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameDoesNotExistException;
import com.peercoin.web.exceptions.IdDoesNotExist;
import com.peercoin.web.models.*;
import com.peercoin.web.models.dtos.OrderDto;
import com.peercoin.web.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unused")
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    private FiatMethodRepository fiatMethodRepository;

    @Autowired
    private FiatRepository fiatRepository;

    @Override
    public Order submitOrder(OrderDto orderDto, String type, String username)
            throws UsernameNotFoundException, CurrencyDoesNotExistException, PaymentMethodNameDoesNotExistException {
        User user=userRepository.getByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        CryptoCoin cryptoCoin=cryptoCoinRepository.getByName(orderDto.getCrypto());
        Currency currency=fiatRepository.getByName(orderDto.getCurrency());
        if (currency==null){
            currency=cryptoCoinRepository.getByName(orderDto.getCurrency());
        }
        FiatMethod fiatMethod=fiatMethodRepository.findByName(orderDto.getPaymentMethod());
        if (cryptoCoin==null || currency == null) {
            throw new CurrencyDoesNotExistException("attempted to use non-existent currency");
        }
        if (fiatMethod == null) {
            throw new PaymentMethodNameDoesNotExistException("attempted to use non-existent payment method");
        }

        Order order = new Order();
        order.setCrypto(cryptoCoin);
        order.setPayment(currency);
        order.setOrderType(type);
        order.setInitiator(user);
        order.setMethod(fiatMethod);
        order.setExchangeRate(orderDto.getExchangeRate());
        order.setMax(orderDto.getMax());
        order.setMin(orderDto.getMin());

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
}

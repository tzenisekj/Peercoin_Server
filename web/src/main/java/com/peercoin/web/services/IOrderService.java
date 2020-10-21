package com.peercoin.web.services;

import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameDoesNotExistException;
import com.peercoin.web.exceptions.IdDoesNotExist;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.dtos.OrderDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IOrderService {
    Order submitOrder(OrderDto orderDto, String type, String username) throws UsernameNotFoundException, CurrencyDoesNotExistException, PaymentMethodNameDoesNotExistException;
    List<Order> getAllOrders();
    Order getOrder(String id) throws IdDoesNotExist;
}

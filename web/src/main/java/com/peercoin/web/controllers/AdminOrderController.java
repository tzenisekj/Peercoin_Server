package com.peercoin.web.controllers;

import com.peercoin.web.models.Order;
import com.peercoin.web.repositories.OrderRepository;
import com.peercoin.web.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}

package com.peercoin.web.controllers;

import com.peercoin.web.models.Order;
import com.peercoin.web.pojos.OrderType;
import com.peercoin.web.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@SuppressWarnings("unused")
public class PeerCoinController {
    @Autowired
    private IOrderService orderService;
    @GetMapping("/")
    public String homepage(Model model, Authentication authentication) {
        if (authentication != null){
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            model.addAttribute("username",userDetails.getUsername());
            model.addAttribute("signedin",true);
        } else{
            model.addAttribute("username","null");
            model.addAttribute("signedin",false);
        }
        List<Order> orders=orderService.getAllOrders();
        List<Order> buyOrders=new ArrayList<>();
        List<Order> sellOrders=new ArrayList<>();
        for (Order order:orders) {
            if (order.getOrderType()== OrderType.BUY){
                buyOrders.add(order);
            }
            if (order.getOrderType() == OrderType.SELL) {
                sellOrders.add(order);
            }
        }
        model.addAttribute("buyOrders",buyOrders);
        model.addAttribute("sellOrders",sellOrders);
        return "home";
    }
}

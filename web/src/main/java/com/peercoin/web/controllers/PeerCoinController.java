package com.peercoin.web.controllers;

import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.displayObjects.OrderDisplayObject;
import com.peercoin.web.pojos.OrderType;
import com.peercoin.web.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
        List<OrderDisplayObject> buyOrders=new ArrayList<>();
        List<OrderDisplayObject> sellOrders=new ArrayList<>();
        for (Order order:orders) {
            if (order.isActive()) {
                User initiator = userRepository.findById(order.getInitiator()).get();
                OrderDisplayObject odo = new OrderDisplayObject(order, initiator);
                if (order.getOrderType() == OrderType.BUY) {
                    buyOrders.add(odo);
                }
                if (order.getOrderType() == OrderType.SELL) {
                    sellOrders.add(odo);
                }
            }
        }
        model.addAttribute("buyOrders",buyOrders);
        model.addAttribute("sellOrders",sellOrders);
        return "home";
    }
}

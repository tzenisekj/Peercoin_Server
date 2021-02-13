package com.peercoin.web.controllers;

import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameDoesNotExistException;
import com.peercoin.web.exceptions.IdDoesNotExist;
import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.displayObjects.OfferDisplayObject;
import com.peercoin.web.models.displayObjects.OrderDisplayObject;
import com.peercoin.web.models.dtos.OfferDto;
import com.peercoin.web.models.dtos.OrderDto;
import com.peercoin.web.pojos.OrderType;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.responses.FailureResponses;
import com.peercoin.web.responses.SuccessResponses;
import com.peercoin.web.services.*;
import com.peercoin.web.services.implementations.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/order")
@SuppressWarnings("unused")
public class OrderController {
    private final Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IFiatMethodService fiatMethodService;

    @Autowired
    private ICryptoCoinService cryptoCoinService;

    @Autowired
    private IFiatService fiatService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity postOrder(@RequestParam("type") String type, @RequestBody OrderDto orderDto, Authentication authentication) {
        try {
            Order order = orderService.submitOrder(orderDto, type, ((UserDetails) authentication.getPrincipal()).getUsername());
        }catch(CurrencyDoesNotExistException | PaymentMethodNameDoesNotExistException e){
            return FailureResponses.failure("Currency or payment method do not exist");
        }
        return SuccessResponses.success("Order Successfully Added");
    }

    @GetMapping("/{id}")
    public Optional<OrderDisplayObject> viewOrder(@PathVariable("id") String id, Authentication authentication) {
        Order order;
        try {
            order = orderService.getOrder(id);
        } catch(IdDoesNotExist e) {
            logger.log(Level.INFO, e.getMessage());
            return Optional.empty();
        }
        User initiator = userRepository.findById(order.getInitiator()).orElse(null);
        OrderDisplayObject odo = new OrderDisplayObject(order, initiator);
        return Optional.of(odo);
    }
    @PostMapping("/{id}/offer")
    public ResponseEntity submitOffer(@PathVariable("id") String id, @RequestBody OfferDto offerdto, Authentication authentication){
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User buyer = userRepository.getByUsername(userDetails.getUsername());
            Order order = orderService.getOrder(id);
            offerService.submitOffer(offerdto, order , buyer);
            return SuccessResponses.success("order placed");
        } catch(IdDoesNotExist e) {
            logger.log(Level.WARNING,e.getMessage());
            return FailureResponses.failure("order not placed, order id does not exist");
        }
    }

    @PostMapping("/{id}/markinactive")
    public ResponseEntity<String> markInactive(@PathVariable("id") String id, Authentication authentication) {
        ResponseEntity<String> returnValue;
        try {
            boolean success = orderService.markInactive(id);
            if (success) {
                returnValue = SuccessResponses.success("order deactivated");
            }
            else {
                returnValue = SuccessResponses.success("order already deactivated");
            }
        } catch (IdDoesNotExist e) {
            logger.log(Level.WARNING, e.getMessage());
            returnValue = FailureResponses.failure("order was not found");
        }
        return returnValue;
    }

    @GetMapping
    public BuyAndSellOrders viewAllOrders() {
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
        return new BuyAndSellOrders(buyOrders, sellOrders);
    }

    private static class BuyAndSellOrders {
        List<OrderDisplayObject> buyOrders;
        List<OrderDisplayObject> sellOrders;
        BuyAndSellOrders(List<OrderDisplayObject> buyOrders, List<OrderDisplayObject> sellOrders) {
            this.buyOrders = buyOrders;
            this.sellOrders = sellOrders;
        }

        public List<OrderDisplayObject> getBuyOrders() {
            return buyOrders;
        }

        public void setBuyOrders(List<OrderDisplayObject> buyOrders) {
            this.buyOrders = buyOrders;
        }

        public List<OrderDisplayObject> getSellOrders() {
            return sellOrders;
        }

        public void setSellOrders(List<OrderDisplayObject> sellOrders) {
            this.sellOrders = sellOrders;
        }
    }
}

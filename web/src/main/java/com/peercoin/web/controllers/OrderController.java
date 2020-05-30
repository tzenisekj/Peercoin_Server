package com.peercoin.web.controllers;

import com.peercoin.core.currency.exceptions.CurrencyDoesNotExistException;
import com.peercoin.core.currency.exceptions.PaymentMethodNameDoesNotExistException;
import com.peercoin.web.exceptions.IdDoesNotExist;
import com.peercoin.web.models.Offer;
import com.peercoin.web.models.Order;
import com.peercoin.web.models.User;
import com.peercoin.web.models.dtos.OfferDto;
import com.peercoin.web.models.dtos.OrderDto;
import com.peercoin.web.repositories.CryptoCoinRepository;
import com.peercoin.web.repositories.FiatMethodRepository;
import com.peercoin.web.repositories.FiatRepository;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.IOrderService;
import com.peercoin.web.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/order")
@SuppressWarnings("unused")
public class OrderController {
    private final Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Autowired
    private IOrderService orderService;

    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    @Autowired
    private FiatRepository fiatRepository;

    @Autowired
    private FiatMethodRepository fiatMethodRepository;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/submit")
    public String submitOrder(Model model, Authentication authentication) {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                model.addAttribute("order",new OrderDto());
                model.addAttribute("cryptos",cryptoCoinRepository.findAll());
                model.addAttribute("fiats",fiatRepository.findAll());
                model.addAttribute("methods",fiatMethodRepository.findAll());
                return "order_form";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/submit")
    public String postOrder(@RequestParam("type") String type,@ModelAttribute("order") OrderDto orderDto, Authentication authentication) {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                try {
                    Order order = orderService.submitOrder(orderDto, type, ((UserDetails) authentication.getPrincipal()).getUsername());
                }catch(CurrencyDoesNotExistException | PaymentMethodNameDoesNotExistException e){
                    return "/error?error=currencydoesnotexist";
                }
                return "redirect:/";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable("id") String id,Model model,Authentication authentication) {
        Order order;
        Offer offer;
        try {
            if (authentication != null){
                UserDetails userDetails=(UserDetails) authentication.getPrincipal();
                model.addAttribute("username",userDetails.getUsername());
                model.addAttribute("signedin",true);
            } else{
                model.addAttribute("username","null");
                model.addAttribute("signedin",false);
            }
            order=orderService.getOrder(id);
        } catch (IdDoesNotExist e) {
            logger.log(Level.INFO,e.getMessage());
            return "/error?error=iddoesnotexist";
        }
        model.addAttribute("order",order);
        model.addAttribute("offer",new OfferDto());
        return "order";
    }
    @PostMapping("/{id}/offer")
    public String submitOffer(@PathVariable("id") String id, @ModelAttribute("offer") OfferDto offerdto, Authentication authentication){
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User buyer = userRepository.getByUsername(userDetails.getUsername());
            Order order = orderService.getOrder(id);
            offerService.submitOffer(offerdto, order , buyer);
            return "redirect:/order/" + id + "?submission=success";
        } catch(IdDoesNotExist e) {
            logger.log(Level.WARNING,e.getMessage());
            return "redirect:/order/" + id + "?submission=error&error=orderdoesnotexist";
        }
    }
}

package com.peercoin.manager.controllers;

import com.peercoin.core.currency.Currency;
import com.peercoin.core.paymentmethods.PaymentMethod;
import com.peercoin.manager.messaging.ISendCurrencyMessage;
import com.peercoin.manager.messaging.ISendPaymentMethodMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    private ISendCurrencyMessage sendCryptoMessage;
    @Autowired
    private ISendPaymentMethodMessage sendPaymentMethodMessage;
    @GetMapping("/admin")
    public String getAdminPage(Model model){
        model.addAttribute("currency", new Currency());
        model.addAttribute("method",new PaymentMethod());
        return "admin";
    }
    @PostMapping("/admin/currency")
    public String setCurrency(@ModelAttribute("currency")Currency currency) {
        sendCryptoMessage.sendMessage(currency);
        return "redirect:/admin?setcurrency=success";
    }
    @PostMapping("/admin/method")
    public String setMethod(@ModelAttribute("method")PaymentMethod paymentMethod) {
        sendPaymentMethodMessage.sendMessage(paymentMethod);
        return "redirect:/admin?setmethod=success";
    }
}

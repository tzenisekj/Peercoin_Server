package com.peercoin.web.controllers;

import com.peercoin.web.models.Notifiable;
import com.peercoin.web.models.User;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<String> getNotifications(Authentication authentication){
        User user = userRepository.getByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        List<Notifiable> allNotification = notificationService.getAllNotification(user);
        List<String> sNotifications = new ArrayList<>();

        for (Notifiable N : allNotification) {
            sNotifications.add(N.notificationMessage());
        }

        return sNotifications;
    }

}

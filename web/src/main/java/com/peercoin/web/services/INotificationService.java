package com.peercoin.web.services;

import com.peercoin.web.models.Notifiable;
import com.peercoin.web.models.User;

import java.util.List;

public interface INotificationService {
    void addNotification(User user, Notifiable notification);
    List<Notifiable> getAllNotification(User user);
    void deleteNotification(User user, int index);
}

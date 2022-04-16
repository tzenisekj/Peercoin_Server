package com.peercoin.web.services.implementations;

import com.peercoin.web.models.Notifiable;
import com.peercoin.web.models.User;
import com.peercoin.web.repositories.UserRepository;
import com.peercoin.web.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class NotificationService implements INotificationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addNotification(User user, Notifiable notification) {
        user.addNotification(notification);
        userRepository.save(user);
    }

    @Override
    public List<Notifiable> getAllNotification(User user){
        return user.getStoredNotifiables();
    }

    @Override
    public void deleteNotification(User user, int index){
        user.getStoredNotifiables().remove(index);
        userRepository.save(user);
    }

}

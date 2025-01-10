package ru.example.notes.service.impl;

import org.springframework.stereotype.Service;
import ru.example.notes.models.User;
import ru.example.notes.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void notifyUser(User user) {
        System.out.println("A new user has been created: " + user.getFirstName());
    }

    @Override
    public String sendNotification(String msg) {
        return msg;
    }
}

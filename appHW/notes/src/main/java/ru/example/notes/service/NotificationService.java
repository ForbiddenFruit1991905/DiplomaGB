package ru.example.notes.service;

import org.springframework.stereotype.Service;
import ru.example.notes.model.User;

@Service
public class NotificationService {

    public void notifyUser(User user) {
        System.out.println("A new user has been created: " + user.getName());
    }

    public String sendNotification(String msg) {
        return msg;
    }
}

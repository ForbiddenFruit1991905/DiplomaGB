package ru.example.notes.service;

import ru.example.notes.models.User;

public interface NotificationService {
    void notifyUser(User user);
    String sendNotification(String msg);
}

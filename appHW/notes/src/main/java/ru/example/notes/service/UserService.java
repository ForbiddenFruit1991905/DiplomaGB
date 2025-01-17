package ru.example.notes.service;

import ru.example.notes.models.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    void init();
    void createUser(User user);
    void saveUser(User user);
    List<User> getUsersList();
    boolean authenticateUser(String email, String password);
}

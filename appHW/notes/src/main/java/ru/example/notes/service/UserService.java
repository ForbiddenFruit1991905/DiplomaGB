package ru.example.notes.service;

import ru.example.notes.models.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    void createUser(User user);
    List<User> getUsersList();
}

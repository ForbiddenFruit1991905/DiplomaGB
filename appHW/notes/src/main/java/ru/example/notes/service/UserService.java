package ru.example.notes.service;

import ru.example.notes.models.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    //TODO запихнуть в контролер
//    @Transactional
//    @PostConstruct // Аннотация для автоматического вызова метода при инициализации (создание ADMIN-а)
    void init();

    void createUser(User user);
    List<User> getUsersList();
}

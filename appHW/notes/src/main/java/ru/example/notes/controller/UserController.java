package ru.example.notes.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.notes.repository.RoleRepository;
import ru.example.notes.repository.UserRepository;
import ru.example.notes.service.impl.UserServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserServiceImpl userServiceImpl;

    @PostMapping("/init")
    public ResponseEntity<String> init() {
        userServiceImpl.init();
        return ResponseEntity.ok("Пользователь ADMIN успешно создан.");
    }
}

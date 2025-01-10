package ru.example.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.notes.models.Role;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.RoleName;
import ru.example.notes.repository.RoleRepository;
import ru.example.notes.repository.UserRepository;
import ru.example.notes.service.impl.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userServiceImpl;

//    @PostMapping("/init")
//    public ResponseEntity<String> init() {
//        userServiceImpl.init();
//        return ResponseEntity.ok("Пользователь ADMIN успешно создан.");
//    }
}

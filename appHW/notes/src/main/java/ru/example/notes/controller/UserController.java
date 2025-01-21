package ru.example.notes.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.models.User;
import ru.example.notes.service.impl.NotificationServiceImpl;
import ru.example.notes.service.impl.UserServiceImpl;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class UserController {
    
    private UserServiceImpl userServiceImpl;
    private NotificationServiceImpl notificationService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @PostMapping("/login/{id}")
//    public String loginUser(@ModelAttribute("user") User user, @PathVariable("id") String id, Model model) {
//        try {
//            UUID.fromString(id.trim());
//            user.setId(UUID.randomUUID());
//        } catch (IllegalArgumentException e) {
//            // Обработка случая некорректного UUID
//            model.addAttribute("error", "Некорректный идентификатор пользователя");
//            return "error";
//        }
//
//        if (userServiceImpl.authenticateUser(user.getEmail(), user.getPassword())) {
//            // Логика успешной аутентификации
//            model.addAttribute("user", userServiceImpl.findByEmail(user.getEmail()));
//            return "redirect:/home";
//        } else {
//            model.addAttribute("error", "Неверный email или пароль");
//            return "login";
//        }
//    }


    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("user") User user, Model model) {
        try {
//            Long userId = Long.parseLong(id);
//            User user = userServiceImpl.findByEmail(u.getEmail());
            if (userServiceImpl.authenticateUser(user.getEmail(), user.getPassword())) {
//                user.setId(userId);
                model.addAttribute("user", user);
                return "redirect:/home";
            } else {
                model.addAttribute("error", "Неверный email или пароль");
                return "login";
            }
        } /*catch (NumberFormatException e) {
            model.addAttribute("error", "Неверный формат идентификатора пользователя");
            return "login";
        } */ catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка во время входа");
            return "login";
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration/save")
    public String registration(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        User registeredUser = userServiceImpl.findByEmail(user.getEmail());
        if (registeredUser != null) {
            result.rejectValue("email", null,
                    "По данному адресу электронной почты уже зарегистрирована учетная запись.");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "registration";
        }
        userServiceImpl.createUser(user);
        userServiceImpl.saveUser(user);
        notificationService.sendNotification("Регистрация выполнена");
        return "redirect:/registration?success";
    }

    //TODO присвоить возможность просмотра только через роль админа
    @GetMapping("/users")
    public String listOfRegisteredUsers(Model model) {
        List<User> users = userServiceImpl.getUsersList();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/init")
    public String init(Model model) {
        userServiceImpl.init();
        model.addAttribute("message", "Пользователь ADMIN успешно создан.");
        return "initSuccess";
    }
}

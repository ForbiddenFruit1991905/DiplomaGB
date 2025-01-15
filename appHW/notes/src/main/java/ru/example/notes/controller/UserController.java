package ru.example.notes.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.models.User;
import ru.example.notes.service.impl.NotificationServiceImpl;
import ru.example.notes.service.impl.UserServiceImpl;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {
    
    private UserServiceImpl userServiceImpl;
    private NotificationServiceImpl notificationService;

    @GetMapping("/")
    public String home() {
        return "registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        User user = new User();
//        userServiceImpl.createUser(user);
        model.addAttribute("user", user);
        //TODO возможно объединить форму регистрации и login а одной странице index(welcome page)!!!
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
        userServiceImpl.createUser(user); // Вызов метода для создания нового пользователя
        userServiceImpl.saveUser(user);
        // Отправка уведомления о выполненной регистрации
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

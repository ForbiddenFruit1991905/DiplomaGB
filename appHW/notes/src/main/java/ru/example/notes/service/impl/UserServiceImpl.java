package ru.example.notes.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.example.notes.models.Role;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.RoleName;
import ru.example.notes.repository.RoleRepository;
import ru.example.notes.repository.UserRepository;
import ru.example.notes.service.UserService;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private NotificationServiceImpl notificationServiceImpl;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //TODO запихнуть в контролер
//    @Transactional
//    @PostConstruct // Аннотация для автоматического вызова метода при инициализации (создание ADMIN-а)
    @Override
    public void init() {
        Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN);
        if (adminRole == checkAdminRoleExist()) {
            Role role = new Role();
            role.setRoleName(RoleName.ADMIN);
//            role.setRoleNameStr("Администратор");
            roleRepository.save(role);

            User adminUser = new User();
            adminUser.setFirstName("Виктория");
            adminUser.setLastName("Лихачёва");
            adminUser.setEmail("email@example.ru");
            adminUser.setPassword(passwordEncoder.encode("veryStrongPassword"));
            adminUser.setRole((Role) List.of(role));
            userRepository.save(adminUser);
        }
    }

    @Override
    public void createUser(User user) {
        User userRegistration = new User();
        userRegistration.setFirstName(user.getFirstName());
        userRegistration.setLastName(user.getLastName());
        userRegistration.setEmail(user.getEmail());
        userRegistration.setPassword(passwordEncoder.encode(user.getPassword()));
        
        notificationServiceImpl.notifyUser(userRegistration);

        Role userRole = roleRepository.findByRoleName(RoleName.USER);
        if (userRole == null) {
            userRole = checkUserRoleExist();
        }

        userRegistration.setRole((Role) List.of(userRole));
        userRepository.save(userRegistration);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    private Role checkUserRoleExist() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        return roleRepository.save(userRole);
    }

    private Role checkAdminRoleExist() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.ADMIN);
        return roleRepository.save(userRole);
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }
}

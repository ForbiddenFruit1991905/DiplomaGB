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

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createUser(User user) {
        User userRegistration = new User();
        userRegistration.setFirstName(user.getFirstName());
        userRegistration.setLastName(user.getLastName());
        userRegistration.setEmail(user.getEmail());

        userRegistration.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleName(RoleName.valueOf("ADMIN"));
        if (role == null) {
            role = checkRoleExist();
        }
        userRegistration.setRoles((Role) List.of(role));
        userRepository.save(userRegistration);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setRoleName(RoleName.valueOf("ADMIN"));
        return roleRepository.save(role);
    }

    @Override
    public List<User> getUsersList() {
        return List.of();
    }
}

package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.notes.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByName(String name);
}

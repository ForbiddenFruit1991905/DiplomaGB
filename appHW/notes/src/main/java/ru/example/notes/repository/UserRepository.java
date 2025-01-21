package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.notes.models.User;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}

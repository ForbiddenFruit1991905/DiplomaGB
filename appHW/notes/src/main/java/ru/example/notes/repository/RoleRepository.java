package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.notes.models.Role;
import ru.example.notes.models.enums.RoleName;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRoleName(RoleName roleName);
}

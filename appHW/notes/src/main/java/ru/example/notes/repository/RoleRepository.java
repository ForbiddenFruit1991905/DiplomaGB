package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.notes.models.Role;
import ru.example.notes.models.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRoleName(RoleName roleName);
}

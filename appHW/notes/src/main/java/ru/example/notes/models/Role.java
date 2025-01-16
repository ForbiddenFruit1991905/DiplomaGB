package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.*;
import ru.example.notes.models.enums.RoleName;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLES")
public class Role {

    /**
     * Отдельный столбец для хранения имени роли
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 50)
    private RoleName roleName;

    /**
     * Имя столбца, которое связывает таблицы users и roles
     */

    @OneToOne(mappedBy = "role")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
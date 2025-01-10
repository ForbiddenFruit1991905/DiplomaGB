package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.*;
import ru.example.notes.models.enums.RoleName;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleNameStr;

    /**
     * Отдельный столбец для хранения имени роли
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 50)
    private RoleName roleName;

    /**
     * Имя столбца, которое связывает таблицы users и roles
     */
    @OneToOne(mappedBy = "roles")
    @JoinColumn(name = "user_id")
    private User user;
}
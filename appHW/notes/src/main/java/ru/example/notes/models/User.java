package ru.example.notes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotEmpty
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty(message = "Адрес электронной почты не должен быть пустым")
    @Email
    @Column(nullable = false, unique = true, length = 250)
    private String email;

    @NotEmpty(message = "Пароль не должен быть пустым, введите пароль")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private List<Note> notes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "owner")
    private Planner planner;
    
    public void addNoteToUser(Note note) {
        note.setUser(this);
        this.getNotes().add(note);
    }

    public void setPlannerToUser(Planner planner) {
        planner.setOwner(this);
        this.setPlanner(planner);
    }

}

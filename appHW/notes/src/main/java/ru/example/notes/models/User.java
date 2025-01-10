package ru.example.notes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /**
     * Имя столбца, которое связывает таблицы users и roles
     */
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //TODO add into NoteController
    public void addNoteToUser(Note note) {
        note.setUser(this);
        this.getNotes().add(note);
    }
}

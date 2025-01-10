package ru.example.notes.models;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 4096)
    private String name;


    @Column(nullable = false, unique = true, length = 4096)
    private String email;

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
    private Role roles;

    //TODO add into NoteController
    public void addNoteToUser(Note note) {
        note.setUser(this);
        this.getNotes().add(note);
    }
}

package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.example.notes.models.enums.NoteStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = " header", nullable = false)
    private String header;
    @Column(name = "text", nullable = false)
    private String text;
    @Column
    @Enumerated(EnumType.STRING)
    private NoteStatus status;
    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;
    //TODO
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Note() {
    }

}

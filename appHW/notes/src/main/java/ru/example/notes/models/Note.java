package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.example.notes.models.enums.NoteStatus;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = " header", nullable = false)
    private String header;

    @Column(name = "text", nullable = false)
    private String text;

    @Column
    @Enumerated(EnumType.STRING)
    private NoteStatus status;

    @Column(name = "created_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    public Note() {
    }
}

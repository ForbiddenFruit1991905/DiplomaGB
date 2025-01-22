package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.example.notes.models.enums.NoteStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(40)", updatable = false, nullable = false)
    private String id;

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

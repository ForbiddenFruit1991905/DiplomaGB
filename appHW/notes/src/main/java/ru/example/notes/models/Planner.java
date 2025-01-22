package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "planner")
public class Planner {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(mappedBy = "planner", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private List<Note> notes;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;
}

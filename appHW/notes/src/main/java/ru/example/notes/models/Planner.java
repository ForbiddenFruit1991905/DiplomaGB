package ru.example.notes.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "planner")
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

//    @Column
//    private Boolean isActive;

    @OneToMany(mappedBy = "planner", cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToOne(mappedBy = "planner")
    @JoinColumn(name = "owner_id")
    private User owner;
}

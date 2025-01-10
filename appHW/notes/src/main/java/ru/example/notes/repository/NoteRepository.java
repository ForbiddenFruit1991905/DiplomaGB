package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByStatus(NoteStatus status);
    List<Note> findByUser(User user);
}

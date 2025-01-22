package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findAllByStatus(NoteStatus status);
    List<Note> findByUser(User user);
    Optional<Note> findById(String id);
    void deleteById(String id);
}

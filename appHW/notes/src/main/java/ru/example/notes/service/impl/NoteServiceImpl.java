package ru.example.notes.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.notes.aspects.TrackUserAction ;
import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;
import ru.example.notes.repository.NoteRepository ;
import ru.example.notes.service.NoteService ;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    @TrackUserAction
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    @TrackUserAction
    public Note getNoteById(String id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Запись не найдена"));
    }

    @Override
    @TrackUserAction
    public Note updateNote(String id, Note noteDetails) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()){
            Note note = optionalNote.get();
            note.setHeader(noteDetails.getHeader());
            note.setText(noteDetails.getText());
            note.setStatus(noteDetails.getStatus());
            return noteRepository.save(note);
        } else {
            throw  new IllegalArgumentException("Запись не найдена");
        }
    }

    @TrackUserAction
    public List<Note> getTasksByStatus(NoteStatus status) {
        return noteRepository.findAllByStatus(status);
    }

    @Override
    @TrackUserAction
    public Note createNote(Note note) {
        note.setCreatedDate(LocalDateTime.now());
        return noteRepository.save(note);
    }
 
    @Override
    @TrackUserAction
    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }
}

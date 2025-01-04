package ru.example.notes.service.impl;

import org.springframework.stereotype.Service;
import ru.example.notes.aspects.TrackUserAction ;
import ru.example.notes.model.Note ;
import ru.example.notes.model.NoteStatus;
import ru.example.notes.repository.NoteRepository ;
import ru.example.notes.service.NoteService ;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    @TrackUserAction
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    @TrackUserAction
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Запись не найдена"));
    }

    @Override
    @TrackUserAction
    public Note updateNote(Long id, Note noteDetails) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()){
            Note note = optionalNote.get();
            note.setHeader(noteDetails.getHeader());
            note.setText(noteDetails.getText());
            note.setStatus(noteDetails.getStatus());
            return noteRepository.save(note);
        } else {
            throw  new IllegalArgumentException("Note is not found");
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
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}

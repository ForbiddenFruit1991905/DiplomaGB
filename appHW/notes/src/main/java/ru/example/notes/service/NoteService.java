package ru.example.notes.service;

import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;
import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    Note getNoteById(String id);
    Note updateNote(String id, Note noteDetails);
    Note createNote(Note note);
    void deleteNote(String id);
    List<Note> getTasksByStatus(NoteStatus status);
    List<Note> getNotesByUser(User user);
}

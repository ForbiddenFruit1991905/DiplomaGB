package ru.example.notes.service;

import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<Note> getAllNotes();
    Note getNoteById(UUID id);
    Note updateNote(UUID id, Note noteDetails);
    Note createNote(Note note);
    void deleteNote(UUID id);
    List<Note> getTasksByStatus(NoteStatus status);
    List<Note> getNotesByUser(User user);
}

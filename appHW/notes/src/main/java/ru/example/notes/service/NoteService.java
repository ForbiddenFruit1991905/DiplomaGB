package ru.example.notes.service;

import ru.example.notes.model.Note ;
import ru.example.notes.model.NoteStatus;

import java.util.List;

public interface NoteService {
    List<Note> getAllNotes();
    Note getNoteById(Long id);
    Note updateNote(Long id, Note noteDetails);
    Note createNote(Note note);
    void deleteNote(Long id);
    List<Note> getTasksByStatus(NoteStatus status);
}

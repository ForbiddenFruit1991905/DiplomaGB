package ru.example.notes.service;

import ru.example.notes.models.Planner;

import java.util.List;

public interface PlannerService {

    Planner createNote(Planner planner);
    List<Planner> getAllNotes();
    Planner getNoteById(Long id);
    Planner updateNote(Planner planner);
    void deleteNote(Long id);
    
}

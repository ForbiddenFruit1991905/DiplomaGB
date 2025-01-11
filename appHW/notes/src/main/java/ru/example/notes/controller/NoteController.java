package ru.example.notes.controller;

import io.micrometer.core.instrument.Counter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.models.Note ;
import ru.example.notes.models.enums.NoteStatus;
import ru.example.notes.service.FileGateway;
import ru.example.notes.service.NoteService ;
import ru.example.notes.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private UserService userService;
    private final FileGateway fileGateway;
    private final Counter counter;
    @GetMapping
    public ResponseEntity<List<Note>> getAll(){
        return ResponseEntity.ok().body(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note){
        note.setCreatedDate(LocalDateTime.now());
        Note createdNote = noteService.createNote(note);
        counter.increment();
        fileGateway.writeToFile(createdNote.getHeader() + ".txt", note.toString());
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        Note noteById;
        try {
            noteById = noteService.getNoteById(id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Note());
        }
        return new ResponseEntity<>(noteById, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") Long id, @RequestBody Note note){
        if (note == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(noteService.updateNote(id, note), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id){
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public List<Note> getTasksByStatus(@PathVariable NoteStatus status) {
        return noteService.getTasksByStatus(status);
    }
}

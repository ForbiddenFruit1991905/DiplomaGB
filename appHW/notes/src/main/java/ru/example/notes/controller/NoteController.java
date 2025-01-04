package ru.example.notes.controller;

import io.micrometer.core.instrument.Counter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.model.Note ;
import ru.example.notes.model.NoteStatus;
import ru.example.notes.service.FileGateway;
import ru.example.notes.service.NoteService ;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final FileGateway fileGateway;
    private final Counter counter;

    public NoteController(NoteService noteService, FileGateway fileGateway, Counter counter) {
        this.noteService = noteService;
        this.fileGateway = fileGateway;
        this.counter = counter;
    }

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

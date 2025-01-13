package ru.example.notes.controller;

import io.micrometer.core.instrument.Counter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.models.Note ;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;
import ru.example.notes.service.FileGateway;
import ru.example.notes.service.NoteService ;
import ru.example.notes.service.UserService;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
public class NoteController {

    private final NoteService noteService;
    private UserService userService;
    private final FileGateway fileGateway;
    private final Counter counter;
    
    @GetMapping("/notes")
    public String getAll(Model model){
        model.addAttribute("notes",noteService.getAllNotes());
        return "notes";
    }

    @PostMapping("/createNote")
    public String createNote(Model model, Note note){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        user.addNoteToUser(note);

        note.setCreatedDate(LocalDateTime.now());
        Note createdNote = noteService.createNote(note);

        List<Note> noteList = noteService.getNotesByUser(user);
        model.addAttribute("notes", noteList);

        counter.increment();
        fileGateway.writeToFile(createdNote.getHeader() + ".txt", note.toString());
        
        return "createNote";
    }

    /**
     * "notFoundView" - имя представления для отображения 404 ошибки;
     * "noteView" - имя представления для отображения информации о заметке
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String getNoteById(@PathVariable("id") Long id, Model model) {
        Note noteById;
        try {
            noteById = noteService.getNoteById(id);
            model.addAttribute("note", noteById);
        } catch (RuntimeException e) {
            model.addAttribute("note", new Note());
            return "notFoundView";
        }
        return "noteView";
    }

    /**
     * "updateNoteView" - имя представления для отображения информации об обновлении заметки
     * @param id
     * @param note
     * @param model
     * @return
     */
    @PutMapping("/{id}")
    public String updateNote(@PathVariable("id") Long id, @RequestBody Note note, Model model) {
        if (note == null) {
            return "notFoundView";
        }
        noteService.updateNote(id, note);
        return "updateNoteView";
    }

    /**
     * "deletedNoteView" - имя представления для подтверждения удаления заметки
     * @param id
     * @param model
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable("id") Long id, Model model) {
        noteService.deleteNote(id);
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "redirect:/getNoteView";
    }

    /**
     * "statusNotesView" - имя представления для отображения заметок по статусу
     * @param status
     * @param model
     * @return
     */
    @GetMapping("/status/{status}")
    public String getTasksByStatus(@PathVariable NoteStatus status, Model model) {
        List<Note> notesByStatus = noteService.getTasksByStatus(status);
        model.addAttribute("notes", notesByStatus);
        return "statusNotesView";
    }
}

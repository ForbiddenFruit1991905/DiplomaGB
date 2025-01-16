package ru.example.notes.controller;

import io.micrometer.core.instrument.Counter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.example.notes.models.Note ;
import ru.example.notes.models.Planner;
import ru.example.notes.models.User;
import ru.example.notes.models.enums.NoteStatus;
import ru.example.notes.service.FileGateway;
import ru.example.notes.service.NoteService ;
import ru.example.notes.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;
    private UserService userService;
    private final FileGateway fileGateway;
    private final Counter counter;

    @GetMapping("/home")
    String home() {
        return "home";
    }
    
    @GetMapping("/notes")
    public String getAllNotesByUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        List<Note> notes = noteService.getNotesByUser(user);
        model.addAttribute("notes",notes);
        return "notesList";
    }

    // TODO Возможно, потребуется создать HTML страницу для отображения успешного создания планера
    // TODO не используется метод createPlanner из сервиса 
    @PostMapping("/createPlanner")
    public String createPlanner(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        Planner planner = new Planner();
        user.setPlannerToUser(planner);
        userService.saveUser(user); // Сохраняем пользователя с новым планером

        return "createPlanner";
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
    public String getNoteById(@PathVariable("id") UUID id, Model model) {
        Note noteById;
        try {
            noteById = noteService.getNoteById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.findByEmail(authentication.getName());
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
    public String updateNote(@PathVariable("id") UUID id, @RequestBody Note note, Model model) {
        if (note == null) {
            return "notFoundView";
        }
//
        noteService.getNoteById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.findByEmail(authentication.getName());
        noteService.updateNote(id, note);
        model.addAttribute("note", note);
        return "redirect:/noteView";
    }

    /**
     * "deletedNoteView" - имя представления для подтверждения удаления заметки
     * @param id
     * @param model
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable("id") UUID id, Model model) {
        Note note = noteService.getNoteById(id);
        User user = note.getUser();
        user.getNotes().remove(note);
        userService.saveUser(user);
        noteService.deleteNote(id);
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "redirect:/noteView";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.findByEmail(authentication.getName());
        model.addAttribute("notes", notesByStatus);
        return "statusNotesView";
    }
}

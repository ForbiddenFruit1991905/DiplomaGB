package ru.example.notes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.example.notes.model.Note;
import ru.example.notes.repository.NoteRepository;
import ru.example.notes.service.impl.NoteServiceImpl;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static ru.example.notes.model.NoteStatus.IN_PROGRESS;
import static ru.example.notes.model.NoteStatus.NOT_STARTED;

public class NoteServiceIntegrationTest {

    @MockBean
    public NoteRepository noteRepository;
    
    @Autowired
    public NoteServiceImpl noteServiceImpl;

    @Test
    public void updateNoteTest() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setStatus(NOT_STARTED);

        Note note2 = new Note();
        note2.setId(2L);
        note2.setStatus(IN_PROGRESS);

        given(noteRepository.findById(note1.getId())).willReturn(Optional.of(note1));

        noteServiceImpl.updateNote(1L, note2);

        verify(noteRepository).findById(1L);
        verify(noteRepository).save(note1);
    }
}

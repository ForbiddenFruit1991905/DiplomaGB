package ru.example.notes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.example.notes.model.Note;
import ru.example.notes.repository.NoteRepository;
import ru.example.notes.service.impl.NoteServiceImpl;

import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static ru.example.notes.model.NoteStatus.IN_PROGRESS;
import static ru.example.notes.model.NoteStatus.NOT_STARTED;

@ExtendWith(MockitoExtension.class)
public class NoteServiceSomeTest {
    @Mock
    public NoteRepository noteRepository;

    @InjectMocks
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
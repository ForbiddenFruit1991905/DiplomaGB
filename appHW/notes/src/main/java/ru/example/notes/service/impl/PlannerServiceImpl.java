package ru.example.notes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.notes.models.Planner;
import ru.example.notes.repository.PlannerRepository;
import ru.example.notes.service.PlannerService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final PlannerRepository plannerRepository;

    @Override
    public Planner createNote(Planner planner) {
        return plannerRepository.save(planner);
    }

    @Override
    public List<Planner> getAllNotes() {
        return plannerRepository.findAll();
    }

    @Override
    public Planner getNoteById(Long id) {
        return plannerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException((Throwable) null));
    }

    @Override
    public Planner updateNote(Planner planner) {
        Planner plannerById = getNoteById(planner.getId());
        plannerById.setDescription(planner.getDescription());
        plannerById.setTitle(planner.getTitle());
        plannerById.setNotes(planner.getNotes());
        plannerById.setOwner(planner.getOwner());
        return plannerRepository.save(plannerById);
    }

    @Override
    public void deleteNote(Long id) {
        Planner plannerById = getNoteById(id);
        plannerRepository.delete(plannerById);
    }

}

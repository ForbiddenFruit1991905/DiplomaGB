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
    public Planner createPlanner(Planner planner) {
        return plannerRepository.save(planner);
    }

    @Override
    public List<Planner> getAllPlanners() {
        return plannerRepository.findAll();
    }

    @Override
    public Planner getPlannerById(String id) {
        return plannerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException((Throwable) null));
    }

    @Override
    public Planner updatePlanner(Planner planner) {
        Planner plannerById = getPlannerById(planner.getId());
        plannerById.setDescription(planner.getDescription());
        plannerById.setTitle(planner.getTitle());
        plannerById.setNotes(planner.getNotes());
        plannerById.setOwner(planner.getOwner());
        return plannerRepository.save(plannerById);
    }

    @Override
    public void deletePlanner(String id) {
        Planner plannerById = getPlannerById(id);
        plannerRepository.delete(plannerById);
    }

}

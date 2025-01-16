package ru.example.notes.service;

import ru.example.notes.models.Planner;

import java.util.List;
import java.util.UUID;

public interface PlannerService {

    Planner createPlanner(Planner planner);
    List<Planner> getAllPlanners();
    Planner getPlannerById(UUID id);
    Planner updatePlanner(Planner planner);
    void deletePlanner(UUID id);
    
}

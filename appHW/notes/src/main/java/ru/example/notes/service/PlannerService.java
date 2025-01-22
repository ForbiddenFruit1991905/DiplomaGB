package ru.example.notes.service;

import ru.example.notes.models.Planner;
import java.util.List;

public interface PlannerService {
    Planner createPlanner(Planner planner);
    List<Planner> getAllPlanners();
    Planner getPlannerById(String id);
    Planner updatePlanner(Planner planner);
    void deletePlanner(String id);
}

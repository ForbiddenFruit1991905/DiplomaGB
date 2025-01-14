package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.notes.models.Planner;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
}

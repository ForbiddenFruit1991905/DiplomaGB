package ru.example.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.notes.models.Planner;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, UUID> {
    Optional<Planner> findById(UUID id);
}

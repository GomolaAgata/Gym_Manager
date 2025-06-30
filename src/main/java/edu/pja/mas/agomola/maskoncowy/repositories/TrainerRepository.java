package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    List<Trainer> findByterminationDate(LocalDate date);
}
package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {
    List<Receptionist> findByterminationDate(LocalDate date);
}
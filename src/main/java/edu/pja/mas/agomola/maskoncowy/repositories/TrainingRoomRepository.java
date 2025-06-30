package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.TrainingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TrainingRoomRepository extends JpaRepository<TrainingRoom, Long> {
    boolean existsByRoomIdAndDateAndTime(Long roomId, LocalDate date, LocalTime time);
}

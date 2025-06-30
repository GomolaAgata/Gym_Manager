package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.Receptionist;
import edu.pja.mas.agomola.maskoncowy.model.Reservation;
import edu.pja.mas.agomola.maskoncowy.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository  extends JpaRepository<Reservation, Long> {
   boolean existsByClientUserPeselAndTrainingIdAndDateAndReservationHour(
            String pesel, Long trainingId, LocalDate date, LocalTime reservationHour);
    boolean existsByClientUserPeselAndTrainingId(String pesel, Long trainingId);

    //Optional<Reservation> findByClientUserPeselAndTrainingId(String pesel, Long trainingId);
    List<Reservation> findByClientUserPeselAndTrainingId(String pesel, Long trainingId);

    List<Reservation> findByClientUserPesel(String pesel);
}

package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.DTO.TrainingDTO;
import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.ClientRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.ReservationRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final TrainingRepository trainingRepository;

    @Transactional
    public Reservation createReservation(Long clientId,
                                         Long trainingId,
                                         LocalDate date,
                                         LocalTime hour,
                                         Duration duration) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));

        if (training instanceof PersonalTraining) {
            long count = training.getReservations().stream()
                    .filter(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED)
                    .count();

            if (count >= 1) {
                throw new IllegalStateException("Personal training already has a reservation.");
            }
        }
        if (training instanceof GroupTraining groupTraining) {

            long activeCount = groupTraining.getReservations().stream()
                    .filter(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED)
                    .count();

            if (activeCount >= groupTraining.maxParticipants) {
                throw new IllegalStateException("Maximum number of participants reached for this group training.");
            }

        }
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setTraining(training);
        reservation.setDate(date);
        reservation.setReservationHour(hour);
        reservation.setLastTime(duration);
        reservation.setState(State.SUBMITTED);

        return reservationRepository.save(reservation);
    }
    public List<Reservation> getActiveReservationsByPesel(String pesel) {
        return reservationRepository.findByClientUserPesel(pesel).stream()
                .filter(r -> r.getState() != null &&
                        (r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED))
                .toList();
    }


    public boolean doesReservationExist(String pesel, Long trainingId) {
        return reservationRepository.existsByClientUserPeselAndTrainingId(pesel, trainingId);
    }
    public boolean hasActiveReservation(String pesel, Long trainingId) {
        List<Reservation> reservations = reservationRepository.findByClientUserPeselAndTrainingId(pesel, trainingId);
        return reservations.stream()
                .anyMatch(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED);
    }



    @Transactional
    public Reservation confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getState() != State.SUBMITTED) {
            throw new IllegalStateException("Only submitted reservations can be confirmed");
        }

        reservation.setState(State.CONFIRMED);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getState() != State.CONFIRMED) {
            throw new IllegalStateException("Only confirmed reservations can be cancelled");
        }

        reservation.setState(State.CANCELLED);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation markAsRealizedIfPast(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getState() == State.CONFIRMED &&
                reservation.getDate().isBefore(LocalDate.now())) {

            reservation.setState(State.COMPLETED);
            return reservationRepository.save(reservation);
        }

        return reservation;
    }
    @Transactional
    public Reservation cancelReservationByPeselAndTrainingId(String pesel, Long trainingId) {
        List<Reservation> reservations = reservationRepository
                .findByClientUserPeselAndTrainingId(pesel, trainingId);

        Reservation reservation = reservations.stream()
                .filter(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Brak aktywnej rezerwacji do anulowania."));

        reservation.setState(State.CANCELLED);
        return reservationRepository.save(reservation);
    }


}

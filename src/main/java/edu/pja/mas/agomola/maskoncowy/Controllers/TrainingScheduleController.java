package edu.pja.mas.agomola.maskoncowy.Controllers;

import edu.pja.mas.agomola.maskoncowy.DTO.ReservationRequestDTO;
import edu.pja.mas.agomola.maskoncowy.DTO.TrainingDTO;
import edu.pja.mas.agomola.maskoncowy.DTO.TrainingScheduleDTO;
import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.ClientRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRoomRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import edu.pja.mas.agomola.maskoncowy.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class TrainingScheduleController {
    private final TrainingRoomRepository trainingRoomRepository;
    private final ReservationService reservationService;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;


    @GetMapping
    public ResponseEntity<List<TrainingScheduleDTO>> getAllSchedules() {
        List<TrainingRoom> trainingRooms = trainingRoomRepository.findAll();

        List<TrainingScheduleDTO> result = trainingRooms.stream().map(tr -> {
            Training training = tr.getTraining();
            Room room = tr.getRoom();
            Gym gym = room.getGym();
            int availableSlots;

            if (training instanceof PersonalTraining) {
                long active = training.getReservations().stream()
                        .filter(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED)
                        .count();

                availableSlots = active == 0 ? 1 : 0;
            } else {
                int activeReservations = (int) training.getReservations().stream()
                        .filter(r -> r.getState() == State.CONFIRMED || r.getState() == State.SUBMITTED)
                        .count();

                 availableSlots = tr.getRoom().getMaxCapacity() - activeReservations;

            }

            return new TrainingScheduleDTO(
                    training.getId(), tr.getId(),
                    String.join(", ", training.getType()),
                    training.getTrainer().getUser().getName() + " " + training.getTrainer().getUser().getSurname(),
                    tr.getDate(),
                    tr.getTime(),
                    availableSlots, room.getRoomNumber(), gym.getName(), gym.getAddress()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public TrainingScheduleDTO getTrainingById(@PathVariable Long id) {
        TrainingRoom trainingRoom = trainingRoomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
        Room room = trainingRoom.getRoom();
        Gym gym = room.getGym();
        Training training = trainingRoom.getTraining();
        return new TrainingScheduleDTO(training.getId(), trainingRoom.getId(),
                String.join(", ", trainingRoom.getTraining().getType()),
                trainingRoom.getTraining().getTrainer().getUser().getName() + " " +
                        trainingRoom.getTraining().getTrainer().getUser().getSurname(),
                trainingRoom.getDate(),
                trainingRoom.getTime(),
                trainingRoom.getTraining().getReservations() == null ?
                        trainingRoom.getRoom().getMaxCapacity() :
                        trainingRoom.getRoom().getMaxCapacity() - trainingRoom.getTraining().getReservations().size(),
                room.getRoomNumber(), gym.getName(), gym.getAddress()
        );
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkReservationExists(
            @RequestParam String pesel,
            @RequestParam Long trainingId) {
    boolean exists = reservationService.hasActiveReservation(pesel, trainingId);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/make")
    public ResponseEntity<?> makeReservation(@RequestBody ReservationRequestDTO dto) {

        Optional<User> user = userRepository.findByPesel(dto.getPesel());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nie znaleziono użytkownika");
        }

        Client client = clientRepository.findByUserId(user.get().getId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono klienta"));

        TrainingRoom trainingRoom = trainingRoomRepository.findById(dto.getTrainingRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono harmonogramu sali"));

        LocalDate date = trainingRoom.getDate();
        LocalTime time = trainingRoom.getTime();

        reservationService.createReservation(
                client.getId(),
                dto.getTrainingId(),
                date,
                time,
                Duration.ofMinutes(60)
        );

        return ResponseEntity.ok("Rezerwacja utworzona");
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelReservation(
            @RequestParam String pesel,
            @RequestParam Long trainingId
    ) {
        try {
            Reservation cancelled = reservationService.cancelReservationByPeselAndTrainingId(pesel, trainingId);
            return ResponseEntity.ok(cancelled);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Wystąpił błąd przy anulowaniu rezerwacji.");
        }
    }


    @GetMapping("/activeReservations")
    public ResponseEntity<List<TrainingDTO>> getActiveReservations(@RequestParam String pesel) {
        List<Reservation> reservations = reservationService.getActiveReservationsByPesel(pesel);

        List<TrainingDTO> result = reservations.stream().map(r -> {
            Training training = r.getTraining();
            List<String> trainingType = training.getType();

            return new TrainingDTO(
                    r.getId(),
                    trainingType,
                    r.getDate(),
                    r.getReservationHour()
            );
        }).toList();

        return ResponseEntity.ok(result);
    }



}

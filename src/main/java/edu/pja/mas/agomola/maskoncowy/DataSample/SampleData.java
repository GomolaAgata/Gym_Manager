package edu.pja.mas.agomola.maskoncowy.DataSample;

import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class SampleData {

    private final ClientService clientService;
    private final TrainerService trainerService;
    private final ReservationService reservationService;
    private final UserService userService;
    private final ReceptionistService receptionistService;
    private final TrainingService trainingService;
    private final GymService gymService;
    private final TrainingRoomService trainingRoomService;
    private final RoomService roomService;

    private final ManagerService managerService;
    private final TrainingPlanService trainingPlanService;

    @PostConstruct
    public void init() {

        User user1 = userService.createUser("12345678901", "Anna", "Nowak", LocalDate.of(1990, 3, 12), "123456789", "anna@example.com");
        User user2 = userService.createUser("23456789012", "Jan", "Kowalski", LocalDate.of(1988, 6, 5), "987654321", "jan@example.com");
        User user3 = userService.createUser("34567890123", "Ewa", "Wiśniewska", LocalDate.of(1995, 8, 20), "555666777", "ewa@example.com");
        User user4 = userService.createUser("45678901234", "Tomasz", "Malinowski", LocalDate.of(1992, 1, 15), "444555666", "tomasz@example.com");
        User user5 = userService.createUser("56789012345", "Karolina", "Dąbrowska", LocalDate.of(1997, 12, 1), "333222111", "karolina@example.com");
        User user6 = userService.createUser("67890123456", "Zofia", "Król", LocalDate.of(1980, 2, 25), "112233445", "zofia@example.com");

        Client client1 = clientService.createClient(user1.getId());
        Client client2 = clientService.createClient(user5.getId());

        Trainer trainer1 = trainerService.createTrainer(user2.getId(), "Fitness", List.of("Certyfikat A", "Certyfikat B"));
        Trainer trainer2 = trainerService.createTrainer(user4.getId(), "Crossfit", List.of("Certyfikat X"));

        Receptionist receptionist = receptionistService.createReceptionist(user3.getId(), LocalDate.now(), BigDecimal.valueOf(2200), Shift.MORNING);

        Manager manager = managerService.createManager(user6.getId(), LocalDate.now(), BigDecimal.valueOf(5000));
        managerService.addSupervisedTrainer(manager.getId(), trainer1.getId());
        managerService.addSupervisedReceptionist(manager.getId(), receptionist.getId());

        Gym gym = gymService.createGym("Gym1", "Siłowniowa 3", "1234567", "silownia@mail", "10-20");
        Room room1 = roomService.createRoom(gym.getId(), 1, 10);
        Room room2 = roomService.createRoom(gym.getId(), 2, 20);

        PersonalTraining personalTraining = trainingService.createPersonalTraining(trainer1, receptionist, gym, List.of("Kardio", "Siłowy"), 5, 60.0);
        GroupTraining groupTraining = trainingService.createGroupTraining(trainer2, receptionist, gym, List.of("Zumba", "Rozciąganie"));
        GroupTraining groupTraining2 = trainingService.createGroupTraining(trainer2, receptionist, gym, List.of("Pilates", "Rozciąganie"));
        GroupTraining groupTraining3 = trainingService.createGroupTraining(trainer1, receptionist, gym, List.of("Aerobik", "Rozciąganie"));

        LocalDate baseDate = LocalDate.of(2025, 6, 7);

        trainingRoomService.scheduleTrainingInRoom(personalTraining.getId(), room1.getId(), baseDate.plusDays(1), LocalTime.of(10, 0));
        trainingRoomService.scheduleTrainingInRoom(groupTraining.getId(), room2.getId(), baseDate.plusDays(2), LocalTime.of(17, 0));
        trainingRoomService.scheduleTrainingInRoom(groupTraining2.getId(), room1.getId(), baseDate.plusDays(3), LocalTime.of(10, 0));
        trainingRoomService.scheduleTrainingInRoom(groupTraining3.getId(), room1.getId(), baseDate.plusDays(4), LocalTime.of(10, 0));

        reservationService.createReservation(client1.getId(), personalTraining.getId(), baseDate.plusDays(1), LocalTime.of(10, 0), Duration.ofMinutes(60));
        reservationService.createReservation(client2.getId(), groupTraining.getId(), baseDate.plusDays(2), LocalTime.of(17, 0), Duration.ofMinutes(60));
        reservationService.createReservation(client2.getId(), groupTraining2.getId(), baseDate.plusDays(3), LocalTime.of(10, 0), Duration.ofMinutes(60));
        reservationService.createReservation(client2.getId(), groupTraining3.getId(), baseDate.plusDays(4), LocalTime.of(10, 0), Duration.ofMinutes(60));

        trainingPlanService.createPlanByTrainer(
                client1.getId(),
                trainer1.getId(),
                "średniozaawansowany",
                "3x w tygodniu",
                baseDate,
                List.of("Pompki", "Przysiady", "Plank")
        );

        trainingPlanService.createPlanByTrainer(
                client2.getId(),
                trainer2.getId(),
                "początkujący",
                "2x w tygodniu",
                baseDate.plusDays(1),
                List.of("Stretching", "Chód na bieżni", "Jazda na rowerku")
        );
    }
}

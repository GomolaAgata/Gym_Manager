package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Trainer;
import edu.pja.mas.agomola.maskoncowy.model.TrainingPlan;
import edu.pja.mas.agomola.maskoncowy.model.User;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainerRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
   private final TrainingPlanService trainingPlanService;


    public Trainer createTrainer(Long userId, String specialization, List<String> certificates) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getTrainer() != null) {
            throw new IllegalStateException("User is already assigned as trainer");
        }

        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setSpecialization(specialization);
        trainer.setCertificates(certificates);
        Trainer savedTrainer = trainerRepository.save(trainer);

        user.setTrainer(savedTrainer);
        userRepository.save(user);

        return savedTrainer;
    }

    @Transactional
    public TrainingPlan prepareTrainingPlan(Long trainerId,
                                            Long clientId,
                                            String skillLevel,
                                            String trainingFrequency,
                                            LocalDate startDate,
                                            List<String> exercises) {

        return trainingPlanService.createPlanByTrainer(trainerId, clientId, skillLevel, trainingFrequency, startDate, exercises);
    }


    public Optional<Trainer> getTrainerById(Long trainerId) {
        return trainerRepository.findById(trainerId);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        User user = trainer.getUser();
        if (user != null) {
            user.setTrainer(null);
            userRepository.save(user);
        }

        trainerRepository.delete(trainer);
    }
}

package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.Trainer;
import edu.pja.mas.agomola.maskoncowy.model.TrainingPlan;
import edu.pja.mas.agomola.maskoncowy.repositories.ClientRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainerRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;


    @Transactional
    public TrainingPlan createPlanByTrainer(Long clientId,
                                           Long trainerId,
                                           String skillLevel,
                                           String trainingFrequency,
                                           LocalDate startDate,
                                           List<String> exercises) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        TrainingPlan plan = new TrainingPlan();
        plan.setClient(client);
        plan.setTrainer(trainer);
        plan.setSkillLevel(skillLevel);
        plan.setTrainingFrequency(trainingFrequency);
        plan.setStartDate(startDate);
        plan.setExercises(exercises);

        return trainingPlanRepository.save(plan);
    }


    public TrainingPlan getTrainingPlan(Long id) {
        return trainingPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training plan not found"));
    }


    public List<TrainingPlan> getAllTrainingPlans() {
        return trainingPlanRepository.findAll();
    }


    @Transactional
    public void updateExercises(Long planId, List<String> exercises) {
        TrainingPlan plan = getTrainingPlan(planId);
        plan.setExercises(exercises);
        trainingPlanRepository.save(plan);
    }


    @Transactional
    public void endPlan(Long planId, LocalDate endDate) {
        TrainingPlan plan = getTrainingPlan(planId);
        plan.setEndDate(endDate);
        trainingPlanRepository.save(plan);
    }


    @Transactional
    public void deleteTrainingPlan(Long id) {
        TrainingPlan plan = getTrainingPlan(id);
        trainingPlanRepository.delete(plan);
    }
}

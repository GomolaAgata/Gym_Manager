package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public GroupTraining createGroupTraining(Trainer trainer, Receptionist receptionist, Gym gym, List<String> types) {
        validateTrainerAndReceptionistAreDifferent(trainer, receptionist);

        GroupTraining groupTraining = new GroupTraining();
        groupTraining.setTrainer(trainer);
        groupTraining.setReceptionist(receptionist);
        groupTraining.setGym(gym);
        groupTraining.setType(types);
        return trainingRepository.save(groupTraining);
    }


    public PersonalTraining createPersonalTraining(Trainer trainer, Receptionist receptionist, Gym gym, List<String> types, int numberOfTrainings, Double duration) {
        validateTrainerAndReceptionistAreDifferent(trainer, receptionist);

        PersonalTraining personalTraining = new PersonalTraining(numberOfTrainings, duration);
        personalTraining.setTrainer(trainer);
        personalTraining.setReceptionist(receptionist);
        personalTraining.setGym(gym);
        personalTraining.setType(types);
        return trainingRepository.save(personalTraining);
    }

    public Training getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }
    public List<GroupTraining> getAllGroupTrainings() {
        return trainingRepository.findAll().stream()
                .filter(t -> t instanceof GroupTraining)
                .map(t -> (GroupTraining) t)
                .toList();
    }

    public List<PersonalTraining> getAllPersonalTrainings() {
        return trainingRepository.findAll().stream()
                .filter(t -> t instanceof PersonalTraining)
                .map(t -> (PersonalTraining) t)
                .toList();
    }
    private void validateTrainerAndReceptionistAreDifferent(Trainer trainer, Receptionist receptionist) {
        if (trainer.getUser().getId().equals(receptionist.getUser().getId())) {
            throw new IllegalArgumentException("Trainer and receptionist must be different persons.");
        }
    }

    @Transactional
    public void deleteTraining(Long trainingId) {
        Training training = getTrainingById(trainingId);
        trainingRepository.delete(training);
    }


}

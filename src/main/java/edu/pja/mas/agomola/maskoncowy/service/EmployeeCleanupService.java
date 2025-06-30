package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Manager;
import edu.pja.mas.agomola.maskoncowy.model.Receptionist;
import edu.pja.mas.agomola.maskoncowy.model.Trainer;
import edu.pja.mas.agomola.maskoncowy.repositories.ManagerRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.ReceptionistRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class EmployeeCleanupService {

        private final TrainerRepository trainerRepository;
        private final ReceptionistRepository receptionistRepository;
        private final ManagerRepository managerRepository;

        @Scheduled(cron = "0 0 2 1 * *", zone = "Europe/Warsaw")
        @Transactional
        public void deleteOldTerminatedEmployees() {
            LocalDate cutoffDate = LocalDate.now().minusYears(5);

            List<Trainer> oldTrainers = trainerRepository.findByterminationDate(cutoffDate);
            trainerRepository.deleteAll(oldTrainers);

            List<Receptionist> oldReceptionists = receptionistRepository.findByterminationDate(cutoffDate);
            receptionistRepository.deleteAll(oldReceptionists);

            List<Manager> oldManagers = managerRepository.findByterminationDate(cutoffDate);
            managerRepository.deleteAll(oldManagers);
        }
    }


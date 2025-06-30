package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.ManagerRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.ReceptionistRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainerRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final ReceptionistRepository receptionistRepository;

    @Transactional
    public Manager createManager(Long userId, LocalDate hireDate, BigDecimal salary) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Manager manager = new Manager();
        manager.setUser(user);
        manager.setHireDate(hireDate);
        manager.setSalary(salary);
        manager.setBonus(BigDecimal.ZERO);

        return managerRepository.save(manager);
    }

    @Transactional
    public void addSupervisedTrainer(Long managerId, Long trainerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        if (manager.getEmployeesSupervised().contains(trainer)) {
            throw new IllegalStateException("Trainer already supervised by this manager");
        }

        manager.getEmployeesSupervised().add(trainer);
        managerRepository.save(manager);
    }
    @Transactional
    public void addSupervisedReceptionist(Long managerId, Long receptionistId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        Receptionist receptionist = receptionistRepository.findById(receptionistId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        if (manager.getEmployeesSupervised().contains(receptionist)) {
            throw new IllegalStateException("Trainer already supervised by this manager");
        }

        manager.getEmployeesSupervised().add(receptionist);
        managerRepository.save(manager);
    }

    @Transactional
    public void grantBonus(Long managerId, Long employeeId, BigDecimal bonusAmount) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        Employee employee = manager.getEmployeesSupervised().stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This employee is not supervised by the manager"));

        BigDecimal salary = employee.getSalary();
        if (salary == null) {
            throw new IllegalStateException("Employee salary must be defined");
        }

        BigDecimal maxAllowedBonus = salary.multiply(BigDecimal.valueOf(0.2));
        BigDecimal currentBonus = employee.getBonus() != null ? employee.getBonus() : BigDecimal.ZERO;
        BigDecimal newBonus = currentBonus.add(bonusAmount);

        if (newBonus.compareTo(maxAllowedBonus) > 0) {
            throw new IllegalArgumentException("Total bonus cannot exceed 20% of salary");
        }

        employee.setBonus(newBonus);

        if (employee instanceof Trainer) {
            trainerRepository.save((Trainer) employee);
        } else if (employee instanceof Receptionist) {
            receptionistRepository.save((Receptionist) employee);
        } else if (employee instanceof Manager) {
            managerRepository.save((Manager) employee);
        } else {
            throw new IllegalStateException("Unknown employee type");
        }
    }


    public List<Employee> getSupervisedEmployees(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
        return manager.getEmployeesSupervised();
    }

    public Manager getManagerById(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
    }
}

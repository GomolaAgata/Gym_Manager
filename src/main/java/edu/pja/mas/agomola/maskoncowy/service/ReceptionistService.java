package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Receptionist;
import edu.pja.mas.agomola.maskoncowy.model.Shift;
import edu.pja.mas.agomola.maskoncowy.model.User;
import edu.pja.mas.agomola.maskoncowy.repositories.ReceptionistRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final UserRepository userRepository;


    @Transactional
    public Receptionist createReceptionist(Long userId,
                                           LocalDate hireDate,
                                           BigDecimal salary,
                                           Shift shift) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getReceptionist() != null) {
            throw new IllegalStateException("User is already assigned as a receptionist");
        }

        Receptionist receptionist = new Receptionist();
        receptionist.setUser(user);
        receptionist.setHireDate(hireDate);
        receptionist.setSalary(salary);
        receptionist.setShift(shift);

        Receptionist saved = receptionistRepository.save(receptionist);
        user.setReceptionist(saved);

        return saved;
    }


    public Receptionist getReceptionist(Long id) {
        return receptionistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receptionist not found"));
    }


    public List<Receptionist> getAllReceptionists() {
        return receptionistRepository.findAll();
    }

    @Transactional
    public void deleteReceptionist(Long id) {
        Receptionist receptionist = receptionistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receptionist not found"));

        receptionist.getUser().setReceptionist(null);
        receptionistRepository.delete(receptionist);
    }
}

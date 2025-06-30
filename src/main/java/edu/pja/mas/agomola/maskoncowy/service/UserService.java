package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.User;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(String pesel,
                           String name,
                           String surname,
                           LocalDate dateOfBirth,
                           String phoneNumber,
                           String emailAddress) {

        if (userRepository.existsByPesel(pesel)) {
            throw new IllegalArgumentException("User with PESEL " + pesel + " already exists.");
        }

        User user = new User();
        user.setPesel(pesel);
        user.setName(name);
        user.setSurname(surname);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        if(emailAddress != null) {
            user.setEmailAddress(emailAddress);
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User updateUserContact(Long userId, String newPhone, String newEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setPhoneNumber(newPhone);
        user.setEmailAddress(newEmail);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.deleteById(id);
    }

    public boolean existsByPesel(String pesel) {
        return userRepository.existsByPesel(pesel);
    }

}

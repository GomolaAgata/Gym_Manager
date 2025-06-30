package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.Status;
import edu.pja.mas.agomola.maskoncowy.model.TrainingPlan;
import edu.pja.mas.agomola.maskoncowy.model.User;
import edu.pja.mas.agomola.maskoncowy.repositories.ClientRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    private static final Set<Long> usedUserIds =  new HashSet<>();
    private final TrainerService trainerService;

    public Client createClient(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User with id " + userId + " does not exist");
        }
        User user = userOpt.get();

        if (user.getClient() != null) {
            throw new IllegalStateException("User is already assigned to another client");
        }

        Client client = new Client();
        client.setUser(user);
        client.setRegistrationDate(LocalDate.now());
        client.setStatus(Status.ACTIVE);

        Client savedClient = clientRepository.save(client);

        user.setClient(savedClient);
        userRepository.save(user);

        return savedClient;
    }

    public TrainingPlan orderTrainingPlan(Long clientId,
                                          Long trainerId,
                                          String skillLevel,
                                          String trainingFrequency,
                                          LocalDate startDate,
                                          List<String> exercises) {

        return trainerService.prepareTrainingPlan(
                trainerId,
                clientId,
                skillLevel,
                trainingFrequency,
                startDate,
                exercises
        );
    }

    public Optional<Client> findClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClientStatus(Long clientId, Status newStatus) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        client.setStatus(newStatus);
        return clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new IllegalArgumentException("Client not found");
        }
        clientRepository.deleteById(clientId);
    }
}

package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUserId(Long id);

    Optional<Object> findByUser(User user);
}

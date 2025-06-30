package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.Client;
import edu.pja.mas.agomola.maskoncowy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPesel(String pesel);
    Optional<User> findByPesel(String pesel);
}

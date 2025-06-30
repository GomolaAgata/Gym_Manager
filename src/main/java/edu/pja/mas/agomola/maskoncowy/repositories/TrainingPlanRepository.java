package edu.pja.mas.agomola.maskoncowy.repositories;

import edu.pja.mas.agomola.maskoncowy.model.TrainingPlan;
import edu.pja.mas.agomola.maskoncowy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanRepository  extends JpaRepository<TrainingPlan, Long> {
}

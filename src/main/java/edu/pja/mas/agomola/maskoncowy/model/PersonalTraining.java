package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Entity
@Data
@NoArgsConstructor
public class PersonalTraining extends Training{

    @Positive
    private int numberOfTrainings;
    @Positive
    private Double duration;

    public PersonalTraining(int numberOfTrainings, Double duration) {
        super();
        this.setPrice(new BigDecimal("100.00"));
        this.numberOfTrainings = numberOfTrainings;
        this.duration = duration;
    }



}

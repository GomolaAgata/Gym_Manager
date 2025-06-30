package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
public class GroupTraining extends Training{
    public static final int maxParticipants = 15;
    public static final int minParticipants = 3;

    public GroupTraining() {
        super();
        this.setPrice(new BigDecimal("50.00"));
    }

}

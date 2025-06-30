package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
public class TrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String skillLevel;
    @NotBlank
    private String trainingFrequency;
    @NotNull
    @PastOrPresent
    private LocalDate startDate;
    private LocalDate endDate;
    @ElementCollection
    @NotEmpty
    private List<String> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Trainer trainer;
}

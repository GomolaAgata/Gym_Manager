package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Trainer extends Employee{
    @NotBlank
    private String specialization;
    @ElementCollection
    @NotEmpty
    private List<String> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "trainer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TrainingPlan> trainingPlans = new HashSet<>();

    @OneToMany(mappedBy = "trainer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Training> trainings = new HashSet<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}

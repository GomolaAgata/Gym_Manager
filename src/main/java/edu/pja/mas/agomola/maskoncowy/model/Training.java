package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @NotNull
    private List<String> type = new ArrayList<>();

    @Positive
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "receptionist_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Receptionist receptionist;

    @OneToMany(mappedBy = "training")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "gym_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gym gym;

    @OneToMany(mappedBy = "training")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TrainingRoom> trainingRooms = new HashSet<>();
}

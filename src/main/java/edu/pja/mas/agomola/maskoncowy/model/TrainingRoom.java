package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @FutureOrPresent
    private LocalDate date;
    @NotNull
    private LocalTime time;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "training_id")
    @EqualsAndHashCode.Exclude
    private Training training;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "room_id")
    @EqualsAndHashCode.Exclude
    private Room room;
}

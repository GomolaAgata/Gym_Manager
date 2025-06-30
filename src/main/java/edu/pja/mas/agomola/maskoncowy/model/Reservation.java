package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime reservationHour;
    @NotNull
    private Duration lastTime;
    @Enumerated(EnumType.STRING)
    @NotNull
    private State state;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Client client;

    @ManyToOne
    @JoinColumn(name = "training_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Training training;

}

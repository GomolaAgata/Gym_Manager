package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receptionist extends Employee{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Shift shift;

    @OneToMany(mappedBy = "receptionist")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Training> trainings = new HashSet<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}

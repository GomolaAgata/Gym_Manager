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
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String emailAddress;
    @NotBlank
    private String openingHours;

    @OneToMany(mappedBy = "gym")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "gym")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Training> trainings = new HashSet<>();
}

package edu.pja.mas.agomola.maskoncowy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Positive
    @Column(unique = true)
    private int roomNumber;
    @Positive
    private int maxCapacity;
    private static final Set<Room> assignedRooms = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "gym_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gym gym;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TrainingRoom> trainingRooms = new ArrayList<>();

    public static boolean isAssigned(Room room) {
        return assignedRooms.contains(room);
    }

    public static void markAsAssigned(Room room) {
        assignedRooms.add(room);
    }

}

package edu.pja.mas.agomola.maskoncowy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TrainingScheduleDTO {
    private Long trainingId;
    private Long trainingRoomId;
    private String trainingName;
    private String trainerFullName;
    private LocalDate date;
    private LocalTime time;
    private int availableSlots;
    private int roomNumber;
    private String gymName;
    private String gymAddress;
}

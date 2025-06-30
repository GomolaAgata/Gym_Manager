package edu.pja.mas.agomola.maskoncowy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TrainingDTO {
    Long reservationId;
    List<String> trainingType;
    LocalDate date;
    LocalTime time;
}

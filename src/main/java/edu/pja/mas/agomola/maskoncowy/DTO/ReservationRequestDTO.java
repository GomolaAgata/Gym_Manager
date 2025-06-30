package edu.pja.mas.agomola.maskoncowy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRequestDTO {

        private String pesel;
        private String firstName;
        private String lastName;
        private Long trainingId;
        private Long trainingRoomId;

}

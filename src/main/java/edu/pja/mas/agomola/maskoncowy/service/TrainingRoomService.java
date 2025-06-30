package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.RoomRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.TrainingRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingRoomService {

    private final TrainingRoomRepository trainingRoomRepository;
    private final RoomRepository roomRepository;
    private final TrainingRepository trainingRepository;

    @Transactional
    public TrainingRoom scheduleTrainingInRoom(Long trainingId, Long roomId, LocalDate date, LocalTime time) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        boolean roomOccupied = trainingRoomRepository
                .existsByRoomIdAndDateAndTime(roomId, date, time);
        if (roomOccupied) {
            throw new IllegalStateException("Room is already scheduled for this date and time");
        }

        TrainingRoom trainingRoom = new TrainingRoom();
        trainingRoom.setRoom(room);
        trainingRoom.setTraining(training);
        trainingRoom.setDate(date);
        trainingRoom.setTime(time);

        return trainingRoomRepository.save(trainingRoom);
    }

    public List<TrainingRoom> getAllSchedules() {
        return trainingRoomRepository.findAll();
    }
    @Transactional
    public void cancelTraining(Long roomTrainingId){
        TrainingRoom trainingRoom = trainingRoomRepository.findById(roomTrainingId)
                .orElseThrow(()-> new IllegalArgumentException("reservation not found"));
       Training training = trainingRoom.getTraining();
       Room room = trainingRoom.getRoom();

       if(room ==null){
           throw new IllegalStateException("No training associated with reservation.");
       }
        LocalDateTime trainingDateTime = trainingRoom.getDate().atTime(trainingRoom.getTime());
        LocalDateTime now = LocalDateTime.now();

        if (Duration.between(now, trainingDateTime).toHours() < 24) {
            throw new IllegalStateException("Reservation can only be cancelled at least 24 hours before the training starts.");
        }
        if (training instanceof GroupTraining) {
            long currentCount = training.getReservations().size();
            if (currentCount <= GroupTraining.minParticipants) {
                trainingRoomRepository.delete(trainingRoom);
            } else {
                throw new IllegalStateException("Minimum number of participants already met – cancellation not allowed.");
            }
        }
    }
}

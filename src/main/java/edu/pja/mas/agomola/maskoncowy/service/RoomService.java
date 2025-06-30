package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.Gym;
import edu.pja.mas.agomola.maskoncowy.model.Room;
import edu.pja.mas.agomola.maskoncowy.repositories.GymRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final GymRepository gymRepository;

    public Room createRoom(Long gymId, int roomNumber, int maxCapacity) {
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            throw new IllegalArgumentException("Room number " + roomNumber + " already exists.");
        }

        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym with ID " + gymId + " not found."));

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setMaxCapacity(maxCapacity);
        room.setGym(gym);

        Room saved = roomRepository.save(room);
        Room.markAsAssigned(saved);

        return saved;
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Transactional
    public Room updateCapacity(Long roomId, int newCapacity) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        room.setMaxCapacity(newCapacity);
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("Room not found");
        }

        Room room = roomRepository.findById(roomId).get();
        roomRepository.deleteById(roomId);
    }

    public boolean isRoomAssigned(Room room) {
        return Room.isAssigned(room);
    }
}

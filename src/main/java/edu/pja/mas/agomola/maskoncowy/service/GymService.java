package edu.pja.mas.agomola.maskoncowy.service;

import edu.pja.mas.agomola.maskoncowy.model.*;
import edu.pja.mas.agomola.maskoncowy.repositories.GymRepository;
import edu.pja.mas.agomola.maskoncowy.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;
    private final RoomRepository roomRepository;

    public Gym createGym(String name, String address, String phoneNumber, String emailAddress, String openingHours) {
        Gym gym = new Gym();
        gym.setName(name);
        gym.setAddress(address);
        gym.setPhoneNumber(phoneNumber);
        gym.setEmailAddress(emailAddress);
        gym.setOpeningHours(openingHours);

        return gymRepository.save(gym);
    }

    public Gym getGymById(Long id) {
        return gymRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found with id: " + id));
    }

    public List<Gym> getAllGyms() {
        return gymRepository.findAll();
    }

    @Transactional
    public void deleteGym(Long gymId) {
        Gym gym = getGymById(gymId);
        gymRepository.delete(gym);
    }

    @Transactional
    public Room addRoomToGym(Long gymId, int roomNumber, int maxCapacity) {
        Gym gym = getGymById(gymId);

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setMaxCapacity(maxCapacity);

        if (Room.isAssigned(room)) {
            throw new IllegalStateException("This room is already assigned to a gym!");
        }

        room.setGym(gym);
        gym.getRooms().add(room);
        Room.markAsAssigned(room);

        return roomRepository.save(room);
    }


    public List<Room> getRoomsByGym(Long gymId) {
        Gym gym = getGymById(gymId);
        return gym.getRooms();
    }
    public User findUserInGymByPesel(Long gymId, String pesel) {
        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new IllegalArgumentException("Gym not found"));

        return gym.getUsers().stream()
                .filter(user -> user.getPesel().equals(pesel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User with given PESEL not found in this gym"));
    }

}

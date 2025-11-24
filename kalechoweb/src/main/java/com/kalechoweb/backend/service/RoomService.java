package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.Room;
import com.kalechoweb.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllActiveRooms() {
        return roomRepository.findByActiveTrue();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(@NonNull Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> getRoomById(@NonNull String id) {
        return roomRepository.findById(id);
    }

    public Room updateRoom(@NonNull String id, @NonNull Room roomDetails) {
        return roomRepository.findById(id).map(room -> {
            room.setName(roomDetails.getName());
            room.setCapacity(roomDetails.getCapacity());
            room.setLocation(roomDetails.getLocation());
            room.setResources(roomDetails.getResources());
            room.setActive(roomDetails.isActive());
            return roomRepository.save(room);
        }).orElse(null);
    }

    public void deleteRoom(@NonNull String id) {
        roomRepository.findById(id).ifPresent(room -> {
            room.setActive(false); // Soft delete
            roomRepository.save(room);
        });
    }
}

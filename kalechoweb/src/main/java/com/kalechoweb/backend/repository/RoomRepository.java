package com.kalechoweb.backend.repository;

import com.kalechoweb.backend.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByActiveTrue();
}

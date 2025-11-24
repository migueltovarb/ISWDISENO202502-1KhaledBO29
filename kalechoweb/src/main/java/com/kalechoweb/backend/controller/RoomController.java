package com.kalechoweb.backend.controller;

import com.kalechoweb.backend.model.Room;
import com.kalechoweb.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms(@RequestParam(required = false, defaultValue = "true") boolean activeOnly) {
        if (activeOnly) {
            return roomService.getAllActiveRooms();
        }
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable @NonNull String id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Room createRoom(@RequestBody @NonNull Room room) {
        return roomService.createRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable @NonNull String id, @RequestBody @NonNull Room room) {
        Room updated = roomService.updateRoom(id, room);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable @NonNull String id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

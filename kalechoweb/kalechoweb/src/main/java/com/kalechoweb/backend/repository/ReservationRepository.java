package com.kalechoweb.backend.repository;

import com.kalechoweb.backend.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByUserId(String userId);

    @Query("{ 'roomId': ?0, 'status': 'CONFIRMED', 'startTime': { $lt: ?2 }, 'endTime': { $gt: ?1 } }")
    List<Reservation> findOverlappingReservations(String roomId, LocalDateTime start, LocalDateTime end);
}

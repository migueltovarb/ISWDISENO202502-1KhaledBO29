package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.Reservation;
import com.kalechoweb.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.lang.NonNull;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        // Basic validation: End time must be after start time
        if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Check for overlaps
        List<Reservation> overlaps = reservationRepository.findOverlappingReservations(
                reservation.getRoomId(),
                reservation.getStartTime(),
                reservation.getEndTime());

        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("Room is already reserved for this time slot");
        }

        reservation.setStatus("CONFIRMED");
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getUserReservations(String userId) {
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(@NonNull String id) {
        reservationRepository.findById(id).ifPresent(reservation -> {
            // In a real app, check if cancellation window is valid
            reservation.setStatus("CANCELLED");
            reservationRepository.save(reservation);
        });
    }
}

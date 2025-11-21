package com.example.vehicles.repository;

import com.example.vehicles.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

// Simple MongoDB repository (Repositorio MongoDB)
public interface VehicleRepository extends MongoRepository<Vehicle, String> {
}
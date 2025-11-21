package com.example.vehicles.controller;

import com.example.vehicles.model.Vehicle;
import com.example.vehicles.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// REST Controller for Vehicle CRUD (Controlador REST CRUD de Vehículos)
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository repo;

    public VehicleController(VehicleRepository repo) {
        this.repo = repo;
    }

    // Get all vehicles (Obtener todos los vehículos)
    @GetMapping
    public List<Vehicle> getAll() {
        return repo.findAll();
    }

    // Get vehicle by ID (Obtener vehículo por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable String id) {
        Optional<Vehicle> vehicle = repo.findById(id);
        return vehicle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a vehicle (Crear vehículo)
    @PostMapping
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return repo.save(vehicle);
    }

    // Update vehicle (Actualizar vehículo)
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable String id, @RequestBody Vehicle vehicle) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setBrand(vehicle.getBrand());
                    existing.setModel(vehicle.getModel());
                    existing.setYear(vehicle.getYear());
                    return ResponseEntity.ok(repo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete vehicle (Eliminar vehículo)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
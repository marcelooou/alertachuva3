package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.Sensor;
import com.fiap.globalsolution.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores") // Base path for sensor endpoints
public class SensorController {

    private final SensorService service;

    @Autowired
    public SensorController(SensorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getAllSensores(
            @RequestParam(required = false) Long idArea) {
        List<Sensor> sensores;
        if (idArea != null) {
            sensores = service.findByIdArea(idArea);
        } else {
            sensores = service.findAll();
        }
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@Valid @RequestBody Sensor sensor) {
        try {
            // Ensure ID is null for creation
            if (sensor.getId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Sensor savedSensor = service.save(sensor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSensor);
        } catch (IllegalArgumentException e) {
            // Handle cases where referenced AreaMonitorada doesn't exist
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Consider returning error message
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @Valid @RequestBody Sensor sensorDetails) {
        try {
            return service.update(id, sensorDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


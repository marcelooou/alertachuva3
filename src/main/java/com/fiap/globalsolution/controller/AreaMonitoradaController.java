package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.AreaMonitorada;
import com.fiap.globalsolution.service.AreaMonitoradaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas") // Define a base path for this controller
public class AreaMonitoradaController {

    private final AreaMonitoradaService service;

    @Autowired
    public AreaMonitoradaController(AreaMonitoradaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AreaMonitorada>> getAllAreas() {
        List<AreaMonitorada> areas = service.findAll();
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaMonitorada> getAreaById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AreaMonitorada> createArea(@Valid @RequestBody AreaMonitorada areaMonitorada) {
        // Ensure ID is null or not set for creation to avoid accidental updates
        if (areaMonitorada.getId() != null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or throw an exception
        }
        AreaMonitorada savedArea = service.save(areaMonitorada);
        // Return 201 Created status with the created resource
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaMonitorada> updateArea(@PathVariable Long id, @Valid @RequestBody AreaMonitorada areaMonitoradaDetails) {
        return service.update(id, areaMonitoradaDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content on successful deletion
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found if the resource doesn't exist
        }
    }
}


package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.LeituraSensor;
import com.fiap.globalsolution.service.LeituraSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/leituras") // Base path for reading endpoints
public class LeituraSensorController {

    private final LeituraSensorService service;

    @Autowired
    public LeituraSensorController(LeituraSensorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LeituraSensor>> getAllLeituras(
            @RequestParam(required = false) Long idSensor) {
        List<LeituraSensor> leituras;
        if (idSensor != null) {
            leituras = service.findByIdSensor(idSensor);
        } else {
            leituras = service.findAll();
        }
        return ResponseEntity.ok(leituras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraSensor> getLeituraById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LeituraSensor> createLeitura(@Valid @RequestBody LeituraSensor leituraSensor) {
        try {
            // Ensure ID is null for creation
            if (leituraSensor.getId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            LeituraSensor savedLeitura = service.save(leituraSensor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLeitura);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Sensor not found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraSensor> updateLeitura(@PathVariable Long id, @Valid @RequestBody LeituraSensor leituraDetails) {
        try {
            return service.update(id, leituraDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Sensor not found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeitura(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints for specific queries/reports ---

    @GetMapping("/stats/media-geral")
    public ResponseEntity<BigDecimal> getMediaGeralValorMedicao() {
        BigDecimal media = service.getAverageValorMedicao();
        return ResponseEntity.ok(media);
    }

    @GetMapping("/stats/contagem-por-sensor/{idSensor}")
    public ResponseEntity<Long> getContagemLeiturasPorSensor(@PathVariable Long idSensor) {
        Long count = service.countLeiturasBySensorId(idSensor);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/reports/top3-sensores-media-24h")
    public ResponseEntity<List<Object[]>> getTop3SensoresMediaUltimas24h() {
        List<Object[]> report = service.getTop3SensoresMediaValorUltimas24h();
        // Consider creating a DTO for a cleaner response structure
        return ResponseEntity.ok(report);
    }
}


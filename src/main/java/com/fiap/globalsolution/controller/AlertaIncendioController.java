package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.AlertaIncendio;
import com.fiap.globalsolution.service.AlertaIncendioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas") // Base path for alert endpoints
public class AlertaIncendioController {

    private final AlertaIncendioService service;

    @Autowired
    public AlertaIncendioController(AlertaIncendioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlertaIncendio>> getAllAlertas(
            @RequestParam(required = false) String nivelSeveridade,
            @RequestParam(required = false) String statusAlerta) {
        List<AlertaIncendio> alertas;
        if (nivelSeveridade != null) {
            alertas = service.findByNivelSeveridade(nivelSeveridade);
        } else if (statusAlerta != null) {
            alertas = service.findByStatus(statusAlerta);
        } else {
            alertas = service.findAll();
        }
        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaIncendio> getAlertaById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlertaIncendio> createAlerta(@Valid @RequestBody AlertaIncendio alertaIncendio) {
        try {
            // Ensure ID is null for creation
            if (alertaIncendio.getId() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            AlertaIncendio savedAlerta = service.save(alertaIncendio);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerta);
        } catch (IllegalArgumentException e) {
            // Handle cases where referenced LeituraSensor or EquipeResposta don't exist
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Consider returning error message
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaIncendio> updateAlerta(@PathVariable Long id, @Valid @RequestBody AlertaIncendio alertaDetails) {
        try {
            return service.update(id, alertaDetails)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints for specific queries/reports ---

    @GetMapping("/reports/detalhes-por-status")
    public ResponseEntity<List<Object[]>> getReportDetalhesAlertasPorStatus(@RequestParam String status) {
        List<Object[]> report = service.getDetalhesAlertasPorStatus(status);
        // Consider DTO for cleaner response
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/areas-com-muitos-alertas")
    public ResponseEntity<List<Object[]>> getReportAreasComMaisDeNAlertas(@RequestParam(defaultValue = "2") int minAlertas) {
        List<Object[]> report = service.getAreasComMaisDeNAlertas(minAlertas);
        // Consider DTO for cleaner response
        return ResponseEntity.ok(report);
    }

    @GetMapping("/stats/contagem-por-area/{idArea}")
    public ResponseEntity<Long> getContagemAlertasPorArea(@PathVariable Long idArea) {
        Long count = service.countAlertasByAreaId(idArea);
        return ResponseEntity.ok(count);
    }
}


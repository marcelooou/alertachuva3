package com.fiap.globalsolution.controller;

import com.fiap.globalsolution.model.EquipeResposta;
import com.fiap.globalsolution.service.EquipeRespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Import DataIntegrityViolationException if using dependency check before delete
// import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@RestController
@RequestMapping("/api/equipes") // Base path for team endpoints
public class EquipeRespostaController {

    private final EquipeRespostaService service;

    @Autowired
    public EquipeRespostaController(EquipeRespostaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EquipeResposta>> getAllEquipes() {
        List<EquipeResposta> equipes = service.findAll();
        return ResponseEntity.ok(equipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeResposta> getEquipeById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EquipeResposta> createEquipe(@Valid @RequestBody EquipeResposta equipeResposta) {
        // Ensure ID is null for creation
        if (equipeResposta.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        EquipeResposta savedEquipe = service.save(equipeResposta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeResposta> updateEquipe(@PathVariable Long id, @Valid @RequestBody EquipeResposta equipeDetails) {
        return service.update(id, equipeDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        // try {
            if (service.deleteById(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        // } catch (DataIntegrityViolationException e) {
            // Handle cases where deletion violates constraints (e.g., team has assigned alerts)
        //     return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        // }
    }

    // --- Endpoints for specific queries/reports ---

    @GetMapping("/reports/contagem-alertas")
    public ResponseEntity<List<Object[]>> getReportEquipesComContagemAlertas() {
        List<Object[]> report = service.getEquipesComContagemAlertas();
        // Consider DTO for cleaner response
        return ResponseEntity.ok(report);
    }

    @GetMapping("/stats/contagem-alertas/{idEquipe}")
    public ResponseEntity<Long> getContagemAlertasPorEquipe(@PathVariable Long idEquipe) {
        Long count = service.countAlertasByEquipeId(idEquipe);
        return ResponseEntity.ok(count);
    }
}


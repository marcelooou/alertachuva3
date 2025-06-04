package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.AlertaIncendio;
import com.fiap.globalsolution.repository.AlertaIncendioRepository;
import com.fiap.globalsolution.repository.EquipeRespostaRepository;
import com.fiap.globalsolution.repository.LeituraSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaIncendioService {

    private final AlertaIncendioRepository alertaRepository;
    private final LeituraSensorRepository leituraRepository; // To validate Leitura existence
    private final EquipeRespostaRepository equipeRepository; // To validate Equipe existence

    @Autowired
    public AlertaIncendioService(AlertaIncendioRepository alertaRepository,
                                 LeituraSensorRepository leituraRepository,
                                 EquipeRespostaRepository equipeRepository) {
        this.alertaRepository = alertaRepository;
        this.leituraRepository = leituraRepository;
        this.equipeRepository = equipeRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertaIncendio> findAll() {
        return alertaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AlertaIncendio> findById(Long id) {
        return alertaRepository.findById(id);
    }

    @Transactional
    public AlertaIncendio save(AlertaIncendio alertaIncendio) {
        // Validate if the referenced LeituraSensor exists
        if (!leituraRepository.existsById(alertaIncendio.getIdLeituraGatilho())) {
            throw new IllegalArgumentException("Leitura Sensor com ID " + alertaIncendio.getIdLeituraGatilho() + " não encontrada.");
        }
        // Validate if the referenced EquipeResposta exists (if provided)
        if (alertaIncendio.getIdEquipeDesignada() != null && !equipeRepository.existsById(alertaIncendio.getIdEquipeDesignada())) {
            throw new IllegalArgumentException("Equipe Resposta com ID " + alertaIncendio.getIdEquipeDesignada() + " não encontrada.");
        }
        return alertaRepository.save(alertaIncendio);
    }

    @Transactional
    public Optional<AlertaIncendio> update(Long id, AlertaIncendio alertaDetails) {
        return alertaRepository.findById(id).map(existingAlerta -> {
            // Validate if the new referenced LeituraSensor exists
            if (!leituraRepository.existsById(alertaDetails.getIdLeituraGatilho())) {
                throw new IllegalArgumentException("Leitura Sensor com ID " + alertaDetails.getIdLeituraGatilho() + " não encontrada.");
            }
            // Validate if the new referenced EquipeResposta exists (if provided)
            if (alertaDetails.getIdEquipeDesignada() != null && !equipeRepository.existsById(alertaDetails.getIdEquipeDesignada())) {
                throw new IllegalArgumentException("Equipe Resposta com ID " + alertaDetails.getIdEquipeDesignada() + " não encontrada.");
            }

            existingAlerta.setIdLeituraGatilho(alertaDetails.getIdLeituraGatilho());
            existingAlerta.setIdEquipeDesignada(alertaDetails.getIdEquipeDesignada()); // Allow null
            existingAlerta.setNivelSeveridade(alertaDetails.getNivelSeveridade());
            existingAlerta.setStatusAlerta(alertaDetails.getStatusAlerta());
            return alertaRepository.save(existingAlerta);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (alertaRepository.existsById(id)) {
            // No direct dependencies to check based on the schema, but could add business logic checks
            alertaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // --- Métodos para consultas/funções específicas ---

    @Transactional(readOnly = true)
    public List<AlertaIncendio> findByNivelSeveridade(String nivel) {
        return alertaRepository.findByNivelSeveridadeIgnoreCase(nivel);
    }

    @Transactional(readOnly = true)
    public List<AlertaIncendio> findByStatus(String status) {
        return alertaRepository.findByStatusAlertaIgnoreCase(status);
    }

    @Transactional(readOnly = true)
    public List<Object[]> getDetalhesAlertasPorStatus(String status) {
        return alertaRepository.findDetalhesAlertasPorStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Object[]> getAreasComMaisDeNAlertas(int minAlertas) {
        return alertaRepository.findAreasComMaisDeNAlertas(minAlertas);
    }

    @Transactional(readOnly = true)
    public Long countAlertasByAreaId(Long idArea) {
        return alertaRepository.countAlertasByIdArea(idArea);
    }
}


package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.EquipeResposta;
import com.fiap.globalsolution.repository.EquipeRespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeRespostaService {

    private final EquipeRespostaRepository repository;
    // Inject AlertaIncendioRepository if needed for dependency checks before deletion
    // private final AlertaIncendioRepository alertaIncendioRepository;

    @Autowired
    public EquipeRespostaService(EquipeRespostaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<EquipeResposta> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EquipeResposta> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public EquipeResposta save(EquipeResposta equipeResposta) {
        // Add validations if necessary
        return repository.save(equipeResposta);
    }

    @Transactional
    public Optional<EquipeResposta> update(Long id, EquipeResposta equipeDetails) {
        return repository.findById(id).map(existingEquipe -> {
            existingEquipe.setNomeEquipe(equipeDetails.getNomeEquipe());
            existingEquipe.setBaseOperacao(equipeDetails.getBaseOperacao());
            return repository.save(existingEquipe);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            // Add logic to check dependencies (AlertaIncendio) before deleting
            // Example: Check if any AlertaIncendio references this EquipeResposta
            // long countAlertas = alertaIncendioRepository.countByIdEquipeDesignada(id);
            // if (countAlertas > 0) {
            //     throw new DataIntegrityViolationException("Não é possível excluir equipe com alertas designados.");
            // }
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // --- Métodos para consultas/funções específicas ---

    @Transactional(readOnly = true)
    public List<Object[]> getEquipesComContagemAlertas() {
        return repository.findEquipesComContagemAlertas();
    }

    @Transactional(readOnly = true)
    public Long countAlertasByEquipeId(Long idEquipe) {
        return repository.countAlertasByIdEquipe(idEquipe);
    }
}


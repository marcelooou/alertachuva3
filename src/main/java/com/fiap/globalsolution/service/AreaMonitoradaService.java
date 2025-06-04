package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.AreaMonitorada;
import com.fiap.globalsolution.repository.AreaMonitoradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AreaMonitoradaService {

    private final AreaMonitoradaRepository repository;

    @Autowired
    public AreaMonitoradaService(AreaMonitoradaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<AreaMonitorada> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AreaMonitorada> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public AreaMonitorada save(AreaMonitorada areaMonitorada) {
        // Validações adicionais podem ser incluídas aqui
        // Por exemplo, verificar se já existe uma área com o mesmo nome
        return repository.save(areaMonitorada);
    }

    @Transactional
    public Optional<AreaMonitorada> update(Long id, AreaMonitorada areaMonitoradaDetails) {
        return repository.findById(id).map(existingArea -> {
            existingArea.setNomeArea(areaMonitoradaDetails.getNomeArea());
            existingArea.setLatitudeCentro(areaMonitoradaDetails.getLatitudeCentro());
            existingArea.setLongitudeCentro(areaMonitoradaDetails.getLongitudeCentro());
            existingArea.setRaioKm(areaMonitoradaDetails.getRaioKm());
            return repository.save(existingArea);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            // Adicionar lógica para verificar dependências (Sensores) antes de deletar, se necessário
            // Ex: verificar se existem sensores associados a esta área
            // Se houver dependências, pode lançar uma exceção ou retornar false
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


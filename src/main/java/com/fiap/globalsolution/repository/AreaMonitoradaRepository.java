package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.AreaMonitorada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaMonitoradaRepository extends JpaRepository<AreaMonitorada, Long> {
    // Métodos de consulta personalizados podem ser adicionados aqui se necessário
    // Exemplo: List<AreaMonitorada> findByNomeAreaContainingIgnoreCase(String nome);
}


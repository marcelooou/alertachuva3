package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    // Exemplo: Encontrar sensores por área
    List<Sensor> findByIdArea(Long idArea);

    // Exemplo: Encontrar sensores por tipo
    List<Sensor> findByTipoSensorIgnoreCase(String tipoSensor);

    // Exemplo: Encontrar sensores por status
    List<Sensor> findByStatusIgnoreCase(String status);
}


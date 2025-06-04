package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.Sensor;
import com.fiap.globalsolution.repository.AreaMonitoradaRepository;
import com.fiap.globalsolution.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final AreaMonitoradaRepository areaMonitoradaRepository; // Inject if needed for validation

    @Autowired
    public SensorService(SensorRepository sensorRepository, AreaMonitoradaRepository areaMonitoradaRepository) {
        this.sensorRepository = sensorRepository;
        this.areaMonitoradaRepository = areaMonitoradaRepository;
    }

    @Transactional(readOnly = true)
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sensor> findById(Long id) {
        return sensorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Sensor> findByIdArea(Long idArea) {
        return sensorRepository.findByIdArea(idArea);
    }

    @Transactional
    public Sensor save(Sensor sensor) {
        // Validate if the referenced AreaMonitorada exists
        if (!areaMonitoradaRepository.existsById(sensor.getIdArea())) {
            // Handle error appropriately - e.g., throw custom exception
            throw new IllegalArgumentException("Área Monitorada com ID " + sensor.getIdArea() + " não encontrada.");
        }
        // Additional validations can be added here
        return sensorRepository.save(sensor);
    }

    @Transactional
    public Optional<Sensor> update(Long id, Sensor sensorDetails) {
        return sensorRepository.findById(id).map(existingSensor -> {
            // Validate if the new referenced AreaMonitorada exists
            if (!areaMonitoradaRepository.existsById(sensorDetails.getIdArea())) {
                throw new IllegalArgumentException("Área Monitorada com ID " + sensorDetails.getIdArea() + " não encontrada.");
            }
            existingSensor.setIdArea(sensorDetails.getIdArea());
            existingSensor.setTipoSensor(sensorDetails.getTipoSensor());
            existingSensor.setLatitude(sensorDetails.getLatitude());
            existingSensor.setLongitude(sensorDetails.getLongitude());
            existingSensor.setStatus(sensorDetails.getStatus());
            return sensorRepository.save(existingSensor);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (sensorRepository.existsById(id)) {
            // Add logic to check dependencies (LeituraSensor) before deleting, if necessary
            sensorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


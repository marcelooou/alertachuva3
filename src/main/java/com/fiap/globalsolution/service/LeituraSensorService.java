package com.fiap.globalsolution.service;

import com.fiap.globalsolution.model.LeituraSensor;
import com.fiap.globalsolution.repository.LeituraSensorRepository;
import com.fiap.globalsolution.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LeituraSensorService {

    private final LeituraSensorRepository leituraRepository;
    private final SensorRepository sensorRepository; // Inject to validate Sensor existence

    @Autowired
    public LeituraSensorService(LeituraSensorRepository leituraRepository, SensorRepository sensorRepository) {
        this.leituraRepository = leituraRepository;
        this.sensorRepository = sensorRepository;
    }

    @Transactional(readOnly = true)
    public List<LeituraSensor> findAll() {
        return leituraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LeituraSensor> findById(Long id) {
        return leituraRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LeituraSensor> findByIdSensor(Long idSensor) {
        return leituraRepository.findByIdSensor(idSensor);
    }

    @Transactional
    public LeituraSensor save(LeituraSensor leituraSensor) {
        // Validate if the referenced Sensor exists
        if (!sensorRepository.existsById(leituraSensor.getIdSensor())) {
            throw new IllegalArgumentException("Sensor com ID " + leituraSensor.getIdSensor() + " não encontrado.");
        }
        // Ensure timestamp is set if not provided (or use DB default)
        if (leituraSensor.getTimestampLeitura() == null) {
            leituraSensor.setTimestampLeitura(LocalDateTime.now());
        }
        return leituraRepository.save(leituraSensor);
    }

    @Transactional
    public Optional<LeituraSensor> update(Long id, LeituraSensor leituraDetails) {
        return leituraRepository.findById(id).map(existingLeitura -> {
            // Validate if the new referenced Sensor exists
            if (!sensorRepository.existsById(leituraDetails.getIdSensor())) {
                throw new IllegalArgumentException("Sensor com ID " + leituraDetails.getIdSensor() + " não encontrado.");
            }
            existingLeitura.setIdSensor(leituraDetails.getIdSensor());
            existingLeitura.setTimestampLeitura(leituraDetails.getTimestampLeitura() != null ? leituraDetails.getTimestampLeitura() : existingLeitura.getTimestampLeitura());
            existingLeitura.setTipoMedicao(leituraDetails.getTipoMedicao());
            existingLeitura.setValorMedicao(leituraDetails.getValorMedicao());
            return leituraRepository.save(existingLeitura);
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (leituraRepository.existsById(id)) {
            // Add logic to check dependencies (AlertaIncendio) before deleting, if necessary
            leituraRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // --- Métodos para consultas/funções específicas ---

    @Transactional(readOnly = true)
    public BigDecimal getAverageValorMedicao() {
        return leituraRepository.findAverageValorMedicao();
    }

    @Transactional(readOnly = true)
    public Long countLeiturasBySensorId(Long idSensor) {
        return leituraRepository.countByIdSensor(idSensor);
    }

    @Transactional(readOnly = true)
    public List<Object[]> getTop3SensoresMediaValorUltimas24h() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusDays(1);
        return leituraRepository.findTop3SensoresMediaValorUltimas24h(twentyFourHoursAgo);
    }
}


package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.LeituraSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeituraSensorRepository extends JpaRepository<LeituraSensor, Long> {

    // Encontrar leituras por sensor
    List<LeituraSensor> findByIdSensor(Long idSensor);

    // Encontrar leituras por tipo de medição
    List<LeituraSensor> findByTipoMedicaoIgnoreCase(String tipoMedicao);

    // Encontrar leituras dentro de um intervalo de tempo
    List<LeituraSensor> findByTimestampLeituraBetween(LocalDateTime start, LocalDateTime end);

    // Consulta para a média geral (similar à fn_media_valor_leitura)
    @Query("SELECT AVG(ls.valorMedicao) FROM LeituraSensor ls")
    BigDecimal findAverageValorMedicao();

    // Consulta para o total de leituras por sensor (similar à fn_total_leituras_por_sensor)
    @Query("SELECT COUNT(ls) FROM LeituraSensor ls WHERE ls.idSensor = :idSensor")
    Long countByIdSensor(@Param("idSensor") Long idSensor);

    // Consulta para o relatório 7.1 (Top 3 sensores com maior média nas últimas 24h)
    // Nota: Esta consulta é complexa e pode ser melhor implementada em um método de serviço
    // ou usando uma Native Query se necessário, dependendo do dialeto SQL.
    // Exemplo simplificado (pode precisar de ajustes para Oracle e performance):
    @Query(value = "SELECT l.id_sensor, ROUND(AVG(l.valor_medicao), 2) as media_valor " +
                   "FROM leitura_sensor l " +
                   "WHERE l.timestamp_leitura >= :startTime " +
                   "GROUP BY l.id_sensor " +
                   "ORDER BY media_valor DESC FETCH FIRST 3 ROWS ONLY", nativeQuery = true)
    List<Object[]> findTop3SensoresMediaValorUltimas24h(@Param("startTime") LocalDateTime startTime);

}


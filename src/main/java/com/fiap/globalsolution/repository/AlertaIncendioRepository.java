package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.AlertaIncendio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaIncendioRepository extends JpaRepository<AlertaIncendio, Long> {

    // Encontrar alertas por nível de severidade (similar ao cursor 6.1)
    List<AlertaIncendio> findByNivelSeveridadeIgnoreCase(String nivelSeveridade);

    // Encontrar alertas por status
    List<AlertaIncendio> findByStatusAlertaIgnoreCase(String statusAlerta);

    // Encontrar alertas por equipe designada
    List<AlertaIncendio> findByIdEquipeDesignada(Long idEquipeDesignada);

    // Consulta para o relatório 7.5 (Detalhamento de alertas abertos)
    @Query(value = "SELECT ai.id_alerta, ai.status_alerta, ls.tipo_medicao, s.tipo_sensor, am.nome_area " +
                   "FROM alerta_incendio ai " +
                   "JOIN leitura_sensor ls ON ai.id_leitura_gatilho = ls.id_leitura " +
                   "JOIN sensor s ON ls.id_sensor = s.id_sensor " +
                   "JOIN area_monitorada am ON s.id_area = am.id_area " +
                   "WHERE ai.status_alerta = :status " +
                   "ORDER BY ai.id_alerta", nativeQuery = true)
    List<Object[]> findDetalhesAlertasPorStatus(@Param("status") String status);

    // Consulta para o relatório 7.2 (Total de alertas por área, > 2 alertas)
    @Query(value = "SELECT am.id_area, am.nome_area, COUNT(*) AS total_alertas " +
                   "FROM alerta_incendio ai " +
                   "JOIN leitura_sensor ls ON ai.id_leitura_gatilho = ls.id_leitura " +
                   "JOIN sensor s ON ls.id_sensor = s.id_sensor " +
                   "JOIN area_monitorada am ON s.id_area = am.id_area " +
                   "GROUP BY am.id_area, am.nome_area " +
                   "HAVING COUNT(*) > :minAlertas " +
                   "ORDER BY total_alertas DESC", nativeQuery = true)
    List<Object[]> findAreasComMaisDeNAlertas(@Param("minAlertas") int minAlertas);

    // Consulta para a função fn_total_alertas_por_area
    @Query(value = "SELECT COUNT(*) " +
                   "FROM ALERTA_INCENDIO ai " +
                   "JOIN LEITURA_SENSOR ls ON ai.ID_LEITURA_GATILHO = ls.ID_LEITURA " +
                   "JOIN SENSOR s ON ls.ID_SENSOR = s.ID_SENSOR " +
                   "WHERE s.ID_AREA = :idArea", nativeQuery = true)
    Long countAlertasByIdArea(@Param("idArea") Long idArea);

}


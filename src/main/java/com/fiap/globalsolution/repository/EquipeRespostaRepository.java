package com.fiap.globalsolution.repository;

import com.fiap.globalsolution.model.EquipeResposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeRespostaRepository extends JpaRepository<EquipeResposta, Long> {

    // Encontrar equipe por nome (exemplo)
    List<EquipeResposta> findByNomeEquipeContainingIgnoreCase(String nomeEquipe);

    // Consulta para o relatório 7.4 (Equipes e quantidade de alertas, incluindo equipes sem alertas)
    @Query(value = "SELECT er.id_equipe, er.nome_equipe, NVL(COUNT(ai.id_alerta), 0) AS total_alertas " +
                   "FROM equipe_resposta er " +
                   "LEFT JOIN alerta_incendio ai ON er.id_equipe = ai.id_equipe_designada " +
                   "GROUP BY er.id_equipe, er.nome_equipe " +
                   "ORDER BY total_alertas DESC", nativeQuery = true)
    List<Object[]> findEquipesComContagemAlertas();

    // Consulta para a função fn_total_alertas_por_equipe
    @Query("SELECT COUNT(a) FROM AlertaIncendio a WHERE a.idEquipeDesignada = :idEquipe")
    Long countAlertasByIdEquipe(@Param("idEquipe") Long idEquipe);

}


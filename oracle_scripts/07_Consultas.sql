-- 7) Consultas SQL de Relatório

-- 7.1) Top 3 sensores com maior média de valor de medição nas últimas 24h
SELECT *
FROM (
  SELECT l.id_sensor,
         ROUND(AVG(l.valor_medicao),2) AS media_valor
    FROM leitura_sensor l
   WHERE l.timestamp_leitura >= SYSTIMESTAMP - INTERVAL '1' DAY
   GROUP BY l.id_sensor
   ORDER BY media_valor DESC
)
WHERE ROWNUM <= 3;

-- 7.2) Total de alertas por área, mostrando apenas áreas com mais de 2 alertas
SELECT am.id_area,       -- Use 'am' aqui
       am.nome_area,
       COUNT(*) AS total_alertas
FROM alerta_incendio ai
JOIN leitura_sensor ls ON ai.id_leitura_gatilho = ls.id_leitura
JOIN sensor s         ON ls.id_sensor        = s.id_sensor
JOIN area_monitorada am ON s.id_area           = am.id_area -- 'am' é o alias correto
GROUP BY am.id_area, am.nome_area -- Use 'am' aqui também
HAVING COUNT(*) > 2
ORDER BY total_alertas DESC;

-- 7.3) Número de leituras por tipo de sensor
SELECT s.tipo_sensor,
       COUNT(*) AS total_leituras
  FROM leitura_sensor ls
  JOIN sensor s ON ls.id_sensor = s.id_sensor
 GROUP BY s.tipo_sensor
 ORDER BY total_leituras DESC;

-- 7.4) Equipes e quantidade de alertas que receberam, incluindo equipes sem alertas
SELECT er.id_equipe,
       er.nome_equipe,
       NVL(COUNT(ai.id_alerta),0) AS total_alertas
  FROM equipe_resposta er
  LEFT JOIN alerta_incendio ai
    ON er.id_equipe = ai.id_equipe_designada
 GROUP BY er.id_equipe, er.nome_equipe
 ORDER BY total_alertas DESC;

-- 7.5) Detalhamento de alertas abertos com ocorrência de leitura e sensor
SELECT ai.id_alerta,
       ai.status_alerta,
       ls.tipo_medicao,
       s.tipo_sensor,
       am.nome_area
  FROM alerta_incendio ai
  JOIN leitura_sensor ls ON ai.id_leitura_gatilho = ls.id_leitura
  JOIN sensor s          ON ls.id_sensor          = s.id_sensor
  JOIN area_monitorada am ON s.id_area             = am.id_area
 WHERE ai.status_alerta = 'ABERTO'
 ORDER BY ai.id_alerta;

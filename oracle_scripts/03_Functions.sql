-- 4) Funções para Retorno de Dados Processados – funcao.sql

CREATE OR REPLACE FUNCTION fn_media_valor_leitura
RETURN NUMBER IS
  v_media NUMBER;
BEGIN
  SELECT AVG(VALOR_MEDICAO)
    INTO v_media
    FROM LEITURA_SENSOR;
  RETURN v_media;
END fn_media_valor_leitura;
/

CREATE OR REPLACE FUNCTION fn_total_leituras_por_sensor(
  p_id_sensor IN SENSOR.ID_SENSOR%TYPE
) RETURN NUMBER IS
  v_total NUMBER;
BEGIN
  SELECT COUNT(*)
    INTO v_total
    FROM LEITURA_SENSOR
   WHERE ID_SENSOR = p_id_sensor;
  RETURN v_total;
END fn_total_leituras_por_sensor;
/

CREATE OR REPLACE FUNCTION fn_total_alertas_por_area(
  p_id_area IN AREA_MONITORADA.ID_AREA%TYPE
) RETURN NUMBER IS
  v_total NUMBER;
BEGIN
  SELECT COUNT(*)
    INTO v_total
    FROM ALERTA_INCENDIO ai
    JOIN LEITURA_SENSOR ls ON ai.ID_LEITURA_GATILHO = ls.ID_LEITURA
    JOIN SENSOR s         ON ls.ID_SENSOR        = s.ID_SENSOR
   WHERE s.ID_AREA = p_id_area;
  RETURN v_total;
END fn_total_alertas_por_area;
/

CREATE OR REPLACE FUNCTION fn_total_alertas_por_equipe(
  p_id_equipe IN EQUIPE_RESPOSTA.ID_EQUIPE%TYPE
) RETURN NUMBER IS
  v_total NUMBER;
BEGIN
  SELECT COUNT(*)
    INTO v_total
    FROM ALERTA_INCENDIO
   WHERE ID_EQUIPE_DESIGNADA = p_id_equipe;
  RETURN v_total;
END fn_total_alertas_por_equipe;
/

-- 3) Procedures DML (INSERT, UPDATE, DELETE) – procedures.sql

-- === AREA_MONITORADA ===
CREATE OR REPLACE PROCEDURE prc_area_insert(
  p_id_area          IN AREA_MONITORADA.ID_AREA%TYPE,
  p_nome_area        IN AREA_MONITORADA.NOME_AREA%TYPE,
  p_latitude_centro  IN AREA_MONITORADA.LATITUDE_CENTRO%TYPE,
  p_longitude_centro IN AREA_MONITORADA.LONGITUDE_CENTRO%TYPE,
  p_raio_km          IN AREA_MONITORADA.RAIO_KM%TYPE
) IS
BEGIN
  INSERT INTO AREA_MONITORADA
    (ID_AREA, NOME_AREA, LATITUDE_CENTRO, LONGITUDE_CENTRO, RAIO_KM)
  VALUES
    (p_id_area, p_nome_area, p_latitude_centro, p_longitude_centro, p_raio_km);
END prc_area_insert;
/

CREATE OR REPLACE PROCEDURE prc_area_update(
  p_id_area          IN AREA_MONITORADA.ID_AREA%TYPE,
  p_nome_area        IN AREA_MONITORADA.NOME_AREA%TYPE,
  p_latitude_centro  IN AREA_MONITORADA.LATITUDE_CENTRO%TYPE,
  p_longitude_centro IN AREA_MONITORADA.LONGITUDE_CENTRO%TYPE,
  p_raio_km          IN AREA_MONITORADA.RAIO_KM%TYPE
) IS
BEGIN
  UPDATE AREA_MONITORADA
     SET NOME_AREA        = p_nome_area,
         LATITUDE_CENTRO  = p_latitude_centro,
         LONGITUDE_CENTRO = p_longitude_centro,
         RAIO_KM          = p_raio_km
   WHERE ID_AREA = p_id_area;
END prc_area_update;
/

CREATE OR REPLACE PROCEDURE prc_area_delete(
  p_id_area IN AREA_MONITORADA.ID_AREA%TYPE
) IS
BEGIN
  DELETE FROM AREA_MONITORADA
   WHERE ID_AREA = p_id_area;
END prc_area_delete;
/

-- === SENSOR ===
CREATE OR REPLACE PROCEDURE prc_sensor_insert(
  p_id_sensor IN SENSOR.ID_SENSOR%TYPE,
  p_id_area   IN SENSOR.ID_AREA%TYPE,
  p_tipo      IN SENSOR.TIPO_SENSOR%TYPE,
  p_lat       IN SENSOR.LATITUDE%TYPE,
  p_lon       IN SENSOR.LONGITUDE%TYPE,
  p_status    IN SENSOR.STATUS%TYPE
) IS
BEGIN
  INSERT INTO SENSOR
    (ID_SENSOR, ID_AREA, TIPO_SENSOR, LATITUDE, LONGITUDE, STATUS)
  VALUES
    (p_id_sensor, p_id_area, p_tipo, p_lat, p_lon, p_status);
END prc_sensor_insert;
/

CREATE OR REPLACE PROCEDURE prc_sensor_update(
  p_id_sensor IN SENSOR.ID_SENSOR%TYPE,
  p_id_area   IN SENSOR.ID_AREA%TYPE,
  p_tipo      IN SENSOR.TIPO_SENSOR%TYPE,
  p_lat       IN SENSOR.LATITUDE%TYPE,
  p_lon       IN SENSOR.LONGITUDE%TYPE,
  p_status    IN SENSOR.STATUS%TYPE
) IS
BEGIN
  UPDATE SENSOR
     SET ID_AREA     = p_id_area,
         TIPO_SENSOR = p_tipo,
         LATITUDE    = p_lat,
         LONGITUDE   = p_lon,
         STATUS      = p_status
   WHERE ID_SENSOR = p_id_sensor;
END prc_sensor_update;
/

CREATE OR REPLACE PROCEDURE prc_sensor_delete(
  p_id_sensor IN SENSOR.ID_SENSOR%TYPE
) IS
BEGIN
  DELETE FROM SENSOR
   WHERE ID_SENSOR = p_id_sensor;
END prc_sensor_delete;
/

-- === LEITURA_SENSOR ===
CREATE OR REPLACE PROCEDURE prc_leitura_insert(
  p_id_leitura    IN LEITURA_SENSOR.ID_LEITURA%TYPE,
  p_id_sensor     IN LEITURA_SENSOR.ID_SENSOR%TYPE,
  p_ts            IN LEITURA_SENSOR.TIMESTAMP_LEITURA%TYPE,
  p_tipo_medicao  IN LEITURA_SENSOR.TIPO_MEDICAO%TYPE,
  p_valor_med     IN LEITURA_SENSOR.VALOR_MEDICAO%TYPE
) IS
BEGIN
  INSERT INTO LEITURA_SENSOR
    (ID_LEITURA, ID_SENSOR, TIMESTAMP_LEITURA, TIPO_MEDICAO, VALOR_MEDICAO)
  VALUES
    (p_id_leitura, p_id_sensor, p_ts, p_tipo_medicao, p_valor_med);
END prc_leitura_insert;
/

CREATE OR REPLACE PROCEDURE prc_leitura_update(
  p_id_leitura    IN LEITURA_SENSOR.ID_LEITURA%TYPE,
  p_id_sensor     IN LEITURA_SENSOR.ID_SENSOR%TYPE,
  p_ts            IN LEITURA_SENSOR.TIMESTAMP_LEITURA%TYPE,
  p_tipo_medicao  IN LEITURA_SENSOR.TIPO_MEDICAO%TYPE,
  p_valor_med     IN LEITURA_SENSOR.VALOR_MEDICAO%TYPE
) IS
BEGIN
  UPDATE LEITURA_SENSOR
     SET ID_SENSOR         = p_id_sensor,
         TIMESTAMP_LEITURA = p_ts,
         TIPO_MEDICAO      = p_tipo_medicao,
         VALOR_MEDICAO     = p_valor_med
   WHERE ID_LEITURA = p_id_leitura;
END prc_leitura_update;
/

CREATE OR REPLACE PROCEDURE prc_leitura_delete(
  p_id_leitura IN LEITURA_SENSOR.ID_LEITURA%TYPE
) IS
BEGIN
  DELETE FROM LEITURA_SENSOR
   WHERE ID_LEITURA = p_id_leitura;
END prc_leitura_delete;
/

-- === EQUIPE_RESPOSTA ===
CREATE OR REPLACE PROCEDURE prc_equipe_insert(
  p_id_equipe IN EQUIPE_RESPOSTA.ID_EQUIPE%TYPE,
  p_nome      IN EQUIPE_RESPOSTA.NOME_EQUIPE%TYPE,
  p_base      IN EQUIPE_RESPOSTA.BASE_OPERACAO%TYPE
) IS
BEGIN
  INSERT INTO EQUIPE_RESPOSTA
    (ID_EQUIPE, NOME_EQUIPE, BASE_OPERACAO)
  VALUES
    (p_id_equipe, p_nome, p_base);
END prc_equipe_insert;
/

CREATE OR REPLACE PROCEDURE prc_equipe_update(
  p_id_equipe IN EQUIPE_RESPOSTA.ID_EQUIPE%TYPE,
  p_nome      IN EQUIPE_RESPOSTA.NOME_EQUIPE%TYPE,
  p_base      IN EQUIPE_RESPOSTA.BASE_OPERACAO%TYPE
) IS
BEGIN
  UPDATE EQUIPE_RESPOSTA
     SET NOME_EQUIPE   = p_nome,
         BASE_OPERACAO = p_base
   WHERE ID_EQUIPE = p_id_equipe;
END prc_equipe_update;
/

CREATE OR REPLACE PROCEDURE prc_equipe_delete(
  p_id_equipe IN EQUIPE_RESPOSTA.ID_EQUIPE%TYPE
) IS
BEGIN
  DELETE FROM EQUIPE_RESPOSTA
   WHERE ID_EQUIPE = p_id_equipe;
END prc_equipe_delete;
/

-- === ALERTA_INCENDIO ===
CREATE OR REPLACE PROCEDURE prc_alerta_insert(
  p_id_alerta          IN ALERTA_INCENDIO.ID_ALERTA%TYPE,
  p_id_leitura_gatilho IN ALERTA_INCENDIO.ID_LEITURA_GATILHO%TYPE,
  p_id_equipe_desig    IN ALERTA_INCENDIO.ID_EQUIPE_DESIGNADA%TYPE,
  p_nivel              IN ALERTA_INCENDIO.NIVEL_SEVERIDADE%TYPE,
  p_status             IN ALERTA_INCENDIO.STATUS_ALERTA%TYPE
) IS
BEGIN
  INSERT INTO ALERTA_INCENDIO
    (ID_ALERTA, ID_LEITURA_GATILHO, ID_EQUIPE_DESIGNADA, NIVEL_SEVERIDADE, STATUS_ALERTA)
  VALUES
    (p_id_alerta, p_id_leitura_gatilho, p_id_equipe_desig, p_nivel, p_status);
END prc_alerta_insert;
/

CREATE OR REPLACE PROCEDURE prc_alerta_update(
  p_id_alerta          IN ALERTA_INCENDIO.ID_ALERTA%TYPE,
  p_id_leitura_gatilho IN ALERTA_INCENDIO.ID_LEITURA_GATILHO%TYPE,
  p_id_equipe_desig    IN ALERTA_INCENDIO.ID_EQUIPE_DESIGNADA%TYPE,
  p_nivel              IN ALERTA_INCENDIO.NIVEL_SEVERIDADE%TYPE,
  p_status             IN ALERTA_INCENDIO.STATUS_ALERTA%TYPE
) IS
BEGIN
  UPDATE ALERTA_INCENDIO
     SET ID_LEITURA_GATILHO  = p_id_leitura_gatilho,
         ID_EQUIPE_DESIGNADA = p_id_equipe_desig,
         NIVEL_SEVERIDADE    = p_nivel,
         STATUS_ALERTA       = p_status
   WHERE ID_ALERTA = p_id_alerta;
END prc_alerta_update;
/

CREATE OR REPLACE PROCEDURE prc_alerta_delete(
  p_id_alerta IN ALERTA_INCENDIO.ID_ALERTA%TYPE
) IS
BEGIN
  DELETE FROM ALERTA_INCENDIO
   WHERE ID_ALERTA = p_id_alerta;
END prc_alerta_delete;
/

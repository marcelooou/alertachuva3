-- 6) Cursores

-- 6.1) Cursor explícito: lista alertas de alta severidade
SET SERVEROUTPUT ON
DECLARE
  CURSOR c_alertas_alta IS
    SELECT id_alerta, nivel_severidade
      FROM ALERTA_INCENDIO
     WHERE nivel_severidade = 'ALTA';
  v_alerta_rec c_alertas_alta%ROWTYPE;
BEGIN
  OPEN c_alertas_alta;
  LOOP
    FETCH c_alertas_alta INTO v_alerta_rec;
    EXIT WHEN c_alertas_alta%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(
      'Alerta ID='||v_alerta_rec.id_alerta||
      ' severidade='||v_alerta_rec.nivel_severidade
    );
  END LOOP;
  CLOSE c_alertas_alta;
END;
/

-- 6.2) Cursor implícito usando FOR LOOP: total de leituras por sensor
SET SERVEROUTPUT ON
BEGIN
  FOR rec IN (
    SELECT id_sensor,
           COUNT(*) AS total_leituras
      FROM LEITURA_SENSOR
     GROUP BY id_sensor
  ) LOOP
    DBMS_OUTPUT.PUT_LINE(
      'Sensor '||rec.id_sensor||
      ' → '||rec.total_leituras||' leituras'
    );
  END LOOP;
END;
/

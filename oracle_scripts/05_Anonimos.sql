-- 5) Blocos Anônimos (arquivo: Anonimos.sql)
SET SERVEROUTPUT ON

-- 5.1) Teste de INSERT/UPDATE/DELETE via procedures
BEGIN
  -- INSERTs
  prc_area_insert(10,'Teste Area',0,0,1);
  prc_sensor_insert(10,10,'Teste',0,0,'ATIVO');
  prc_leitura_insert(10,10,SYSTIMESTAMP,'Teste',1.1);
  prc_equipe_insert(10,'Equipe Teste','Base X');
  prc_alerta_insert(10,10,10,'BAIXA','ABERTO');

  -- UPDATEs
  prc_area_update(10,'Teste Area Mod',1,1,2);
  prc_sensor_update(10,10,'TesteMod',1,1,'INATIVO');
  prc_leitura_update(10,10,SYSTIMESTAMP,'TesteMod',2.2);
  prc_equipe_update(10,'Equipe Mod','Base Y');
  prc_alerta_update(10,10,10,'MÉDIA','FECHADO');

  -- DELETEs
  prc_alerta_delete(10);
  prc_equipe_delete(10);
  prc_leitura_delete(10);
  prc_sensor_delete(10);
  prc_area_delete(10);

  COMMIT;
END;
/

-- 5.2) IF/ELSE e loop simples
BEGIN
  DECLARE
    v_media NUMBER := fn_media_valor_leitura;
  BEGIN
    IF v_media > 50 THEN
      DBMS_OUTPUT.PUT_LINE('ALERTA: média alta = '||TO_CHAR(v_media));
    ELSE
      DBMS_OUTPUT.PUT_LINE('Média normal = '||TO_CHAR(v_media));
    END IF;
  
    FOR rec IN (
      SELECT id_area, COUNT(*) cnt
        FROM alerta_incendio ai
        JOIN leitura_sensor ls ON ai.id_leitura_gatilho = ls.id_leitura
        JOIN sensor s        ON ls.id_sensor         = s.id_sensor
       GROUP BY id_area
    ) LOOP
      IF rec.cnt > 2 THEN
        DBMS_OUTPUT.PUT_LINE(
          'Área '||rec.id_area||' possui muitos alertas: '||rec.cnt
        );
      ELSE
        DBMS_OUTPUT.PUT_LINE(
          'Área '||rec.id_area||' dentro do normal: '||rec.cnt
        );
      END IF;
    END LOOP;
  END;
END;
/

-- 5.3) Iteração com cursor explícito
DECLARE
  CURSOR c_areas IS
    SELECT id_area, nome_area FROM area_monitorada;
  v_rec c_areas%ROWTYPE;
BEGIN
  FOR v_rec IN c_areas LOOP
    DBMS_OUTPUT.PUT_LINE(
      'Área '||v_rec.id_area||' - '||v_rec.nome_area
    );
  END LOOP;
END;
/

-- 5.4) Limpeza condicional em bloco anônimo
BEGIN
  FOR rec IN (
    SELECT id_alerta
      FROM alerta_incendio
     WHERE status_alerta = 'FECHADO'
  ) LOOP
    prc_alerta_delete(rec.id_alerta);
  END LOOP;
  COMMIT;
END;
/

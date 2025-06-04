BEGIN
  -- popule AREA_MONITORADA via proc_area_insert
  prc_area_insert(1,'Floresta A',-23.550520,-46.633308,10);
  prc_area_insert(2,'Parque B',  -22.906847,-43.172896, 5);
  prc_area_insert(3,'Mata C',    -15.794229,-47.882166, 8);
  prc_area_insert(4,'Reserva D',  -3.119028, -60.021731,12);
  prc_area_insert(5,'Bosque E',  -19.924502,-43.935238, 7);

  -- popule SENSOR via prc_sensor_insert
  prc_sensor_insert(1,1,'Temperatura',-23.550520,-46.633308,'ATIVO');
  prc_sensor_insert(2,1,'Fumaça',     -23.551000,-46.632000,'ATIVO');
  prc_sensor_insert(3,2,'Temperatura',-22.906847,-43.172896,'ATIVO');
  prc_sensor_insert(4,3,'CO2',        -15.794229,-47.882166,'ATIVO');
  prc_sensor_insert(5,4,'Temperatura', -3.119028, -60.021731,'INATIVO');

  -- popule LEITURA_SENSOR via prc_leitura_insert
  prc_leitura_insert(1,1,SYSTIMESTAMP,'Temperatura',35.2);
  prc_leitura_insert(2,2,SYSTIMESTAMP,'Fumaça',     8.5);
  prc_leitura_insert(3,3,SYSTIMESTAMP,'Temperatura',33.1);
  prc_leitura_insert(4,4,SYSTIMESTAMP,'CO2',      1200.0);
  prc_leitura_insert(5,5,SYSTIMESTAMP,'Temperatura',29.9);

  -- popule EQUIPE_RESPOSTA via prc_equipe_insert
  prc_equipe_insert(1,'Brigada A','Base Norte');
  prc_equipe_insert(2,'Brigada B','Base Sul');
  prc_equipe_insert(3,'Brigada C','Base Leste');
  prc_equipe_insert(4,'Brigada D','Base Oeste');
  prc_equipe_insert(5,'Brigada E','Base Central');

  -- popule ALERTA_INCENDIO via prc_alerta_insert
  prc_alerta_insert(1,2,1,'ALTA',      'ABERTO');
  prc_alerta_insert(2,3,2,'MÉDIA',     'ABERTO');
  prc_alerta_insert(3,4,3,'BAIXA',     'FECHADO');
  prc_alerta_insert(4,1,NULL,'ALTA',    'ABERTO');
  prc_alerta_insert(5,5,4,'MÉDIA',     'EM ANDAMENTO');

  COMMIT;
END;
/



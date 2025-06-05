-- Script para criar o utilizador RM558254 e conceder as permissões necessárias
-- Este script deve ser executado antes de todos os outros scripts SQL

-- Criar o utilizador com a senha fornecida
CREATE USER RM558254 IDENTIFIED BY 280705;

-- Conceder privilégios básicos necessários para conexão e operações
GRANT CONNECT TO RM558254;
GRANT RESOURCE TO RM558254;
GRANT CREATE SESSION TO RM558254;
GRANT CREATE TABLE TO RM558254;
GRANT CREATE VIEW TO RM558254;
GRANT CREATE SEQUENCE TO RM558254;
GRANT CREATE PROCEDURE TO RM558254;
GRANT UNLIMITED TABLESPACE TO RM558254;

-- Definir o esquema padrão para o utilizador
ALTER USER RM558254 DEFAULT TABLESPACE USERS;

-- Confirmar as alterações
COMMIT;

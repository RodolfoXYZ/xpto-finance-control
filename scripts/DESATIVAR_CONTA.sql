--------------------------------------------------------
--  Arquivo criado - sexta-feira-maio-09-2025   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Procedure DESATIVAR_CONTA
--------------------------------------------------------
set define off;

  CREATE OR REPLACE NONEDITIONABLE PROCEDURE "SYSTEM"."DESATIVAR_CONTA" (p_id IN NUMBER) AS
  v_count NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_count FROM MOVIMENTACAO WHERE CONTA_ID = p_id;
  IF v_count > 0 THEN
    UPDATE conta SET ativa = 0 WHERE conta_id = p_id;
  ELSE
    DELETE FROM conta WHERE conta_id = p_id;
  END IF;
END;

/

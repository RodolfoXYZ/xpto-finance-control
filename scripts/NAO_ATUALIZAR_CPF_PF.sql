--------------------------------------------------------
--  Arquivo criado - sexta-feira-maio-09-2025   
--------------------------------------------------------
CREATE OR REPLACE TRIGGER NAO_ATUALIZAR_CPF_PF
BEFORE UPDATE ON PESSOA_FISICA
FOR EACH ROW
BEGIN
    IF :NEW.CPF <> :OLD.CPF THEN
        RAISE_APPLICATION_ERROR(-20010, 'O CAMPO "CPF" NAO PODE SER ALTERADO.');
    END IF;
END;
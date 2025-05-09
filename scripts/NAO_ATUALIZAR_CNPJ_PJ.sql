--------------------------------------------------------
--  Arquivo criado - sexta-feira-maio-09-2025   
--------------------------------------------------------
CREATE OR REPLACE TRIGGER NAO_ATUALIZAR_CNPJ_PJ
BEFORE UPDATE ON PESSOA_JURIDICA
FOR EACH ROW
BEGIN
    IF :NEW.CNPJ <> :OLD.CNPJ THEN
        RAISE_APPLICATION_ERROR(-20010, 'O CAMPO "CNPJ" NAO PODE SER ALTERADO.');
    END IF;
END;
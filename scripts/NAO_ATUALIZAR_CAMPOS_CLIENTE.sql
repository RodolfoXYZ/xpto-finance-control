--------------------------------------------------------
--  Arquivo criado - sexta-feira-maio-09-2025   
--------------------------------------------------------
CREATE OR REPLACE TRIGGER NAO_ATUALIZAR_CAMPOS_CLIENTE
BEFORE UPDATE ON CLIENTE
FOR EACH ROW
BEGIN
    IF :NEW.DATA_CADASTRO <> :OLD.DATA_CADASTRO THEN
        RAISE_APPLICATION_ERROR(-20001, 'O CAMPO "DATA_CADASTRO" NAO PODE SER ALTERADO.');
    END IF;

    IF :NEW.NOME <> :OLD.NOME THEN
        RAISE_APPLICATION_ERROR(-20002, 'O CAMPO "NOME" NAO PODE SER ALTERADO.');
    END IF;

    IF :NEW.TIPO_CLIENTE <> :OLD.TIPO_CLIENTE THEN
        RAISE_APPLICATION_ERROR(-20003, 'O CAMPO "TIPO_CLIENTE" NAO PODE SER ALTERADO.');
    END IF;
END;
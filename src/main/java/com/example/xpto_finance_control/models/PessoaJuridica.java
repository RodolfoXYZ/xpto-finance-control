package com.example.xpto_finance_control.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class PessoaJuridica extends Cliente {
    private String cnpj;
}

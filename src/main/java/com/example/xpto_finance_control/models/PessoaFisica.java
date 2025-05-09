package com.example.xpto_finance_control.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class PessoaFisica extends Cliente {
    private String cpf;
}

package com.example.xpto_finance_control.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enderecoId;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @Override
    public String toString() {
        return  "Rua= '" + rua + '\'' +
                ", Numero= '" + numero + '\'' +
                ", Complemento= '" + complemento + '\'' +
                ", Bairro= '" + bairro + '\'' +
                ", Cidade= '" + cidade + '\'' +
                ", Estado= '" + estado + '\'' +
                ", CEP= '" + cep + '\'' +
                '}';
    }
}

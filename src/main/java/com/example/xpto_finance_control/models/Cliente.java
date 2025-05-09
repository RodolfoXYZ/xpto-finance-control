package com.example.xpto_finance_control.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;
    private String nome;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;
    private LocalDate dataCadastro;
    @OneToOne
    private Endereco endereco;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("cliente")
    private List<Conta> contas = new ArrayList<>();;
}

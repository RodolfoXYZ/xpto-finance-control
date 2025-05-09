package com.example.xpto_finance_control.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contaId;
    private String banco;
    private String agencia;
    private String numeroConta;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("contas")
    private Cliente cliente;
    private BigDecimal saldo = BigDecimal.ZERO;
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"cliente", "conta"})
    private List<Movimentacao> movimentacoes = new ArrayList<>();;
    private boolean ativa = true;
}

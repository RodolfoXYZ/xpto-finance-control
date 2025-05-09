package com.example.xpto_finance_control.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimentacaoId;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnoreProperties("contas")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    @JsonIgnoreProperties({"movimentacoes", "cliente"})
    private Conta conta;
    private BigDecimal valor;
    private LocalDate dataMovimentacao;

}

package com.example.xpto_finance_control.DTOs;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record PessoaJuridicaDTO(@NotNull String nome,@NotNull EnderecoDTO endereco,@NotNull ContaSimplificadaDTO contaInicial,@NotNull BigDecimal movimentacaoInicial,@NotNull String telefone,@NotNull String cnpj) {
}

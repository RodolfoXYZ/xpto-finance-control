package com.example.xpto_finance_control.DTOs;

import com.example.xpto_finance_control.models.TipoMovimentacao;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovimentacaoDTO(@NotNull TipoMovimentacao tipoMovimentacao,@NotNull Long clientId,@NotNull Long contaId,@NotNull BigDecimal valor,@NotNull LocalDate dataMovimentacao) {
}

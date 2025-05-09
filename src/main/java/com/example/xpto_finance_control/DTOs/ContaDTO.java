package com.example.xpto_finance_control.DTOs;

import jakarta.validation.constraints.NotNull;

public record ContaDTO(@NotNull String banco,@NotNull String agencia,@NotNull String numeroConta,@NotNull Long clienteId) {
}

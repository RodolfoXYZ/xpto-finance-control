package com.example.xpto_finance_control.DTOs;

import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(@NotNull String rua,@NotNull String numero,@NotNull String complemento,@NotNull String bairro,@NotNull String cidade,@NotNull String estado,@NotNull String cep) {
}

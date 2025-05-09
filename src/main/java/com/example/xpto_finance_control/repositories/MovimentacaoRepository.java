package com.example.xpto_finance_control.repositories;

import com.example.xpto_finance_control.models.Conta;
import com.example.xpto_finance_control.models.Movimentacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByDataMovimentacaoBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Movimentacao> findByContaAndDataMovimentacaoBetween(Conta conta, LocalDate dataInicio, LocalDate dataFim);
    List<Movimentacao> findByConta(Conta conta);
}

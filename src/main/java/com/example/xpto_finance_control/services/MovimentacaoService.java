package com.example.xpto_finance_control.services;

import com.example.xpto_finance_control.DTOs.MovimentacaoDTO;
import com.example.xpto_finance_control.models.*;
import com.example.xpto_finance_control.repositories.ContaRepository;
import com.example.xpto_finance_control.repositories.MovimentacaoRepository;
import com.example.xpto_finance_control.repositories.PessoaFisicaRepository;
import com.example.xpto_finance_control.repositories.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    public Movimentacao cadastrarMovimentacao(MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setTipoMovimentacao(movimentacaoDTO.tipoMovimentacao());
        Cliente cliente = buscarCliente(movimentacaoDTO.clientId());
        movimentacao.setCliente(cliente);
        Conta conta = contaRepository.findById(movimentacaoDTO.contaId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + movimentacaoDTO.contaId()));
        movimentacao.setConta(conta);
        movimentacao.setValor(movimentacaoDTO.valor());
        movimentacao.setDataMovimentacao(movimentacaoDTO.dataMovimentacao());
        BigDecimal valor = movimentacaoDTO.valor();
        if (movimentacaoDTO.tipoMovimentacao() == TipoMovimentacao.CREDITO) {
            // Depósito: soma ao saldo
            conta.setSaldo(conta.getSaldo().add(valor));
        } else if (movimentacaoDTO.tipoMovimentacao() == TipoMovimentacao.DEBITO) {
            // Retirada: subtrai do saldo, se houver saldo suficiente
            if (conta.getSaldo().compareTo(valor) >= 0) {
                conta.setSaldo(conta.getSaldo().subtract(valor));
            } else {
                throw new RuntimeException("Saldo insuficiente para realizar a movimentação.");
            }
        }
        contaRepository.save(conta);
        return movimentacaoRepository.save(movimentacao);
    }

    public List<Movimentacao> listarMovimentacoes() {
        return movimentacaoRepository.findAll();
    }

    // public Movimentacao atualizarMovimentacao(Long id, MovimentacaoDTO movimentacaoDTO) {
    //     Movimentacao movimentacao = movimentacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));
    //     movimentacao.setTipoMovimentacao(movimentacaoDTO.tipoMovimentacao());
    //     movimentacao.setDataMovimentacao(movimentacaoDTO.dataMovimentacao());
    //     movimentacao.setValor(movimentacaoDTO.valor());
    //     return movimentacaoRepository.save(movimentacao);
    // }

    public void deletarMovimentacao(Long id) {
        movimentacaoRepository.deleteById(id);
    }

    public Movimentacao criarMovimentacaoInicial(Conta conta, BigDecimal valorInicial) {
        Movimentacao movimentacaoInicial = new Movimentacao();
        movimentacaoInicial.setConta(conta);
        movimentacaoInicial.setCliente(conta.getCliente());
        movimentacaoInicial.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoInicial.setValor(valorInicial);
        movimentacaoInicial.setDataMovimentacao(java.time.LocalDate.now());
        conta.setSaldo(conta.getSaldo().add(valorInicial));
        contaRepository.save(conta);
        return movimentacaoRepository.save(movimentacaoInicial);
    }

    private Cliente buscarCliente(Long clientId) {
        Optional<PessoaFisica> pessoaFisica = pessoaFisicaRepository.findById(clientId);
        if (pessoaFisica.isPresent()) {
            return pessoaFisica.get();
        }
        Optional<PessoaJuridica> pessoaJuridica = pessoaJuridicaRepository.findById(clientId);
        if (pessoaJuridica.isPresent()) {
            return pessoaJuridica.get();
        }
        throw new RuntimeException("Cliente não encontrado com ID: " + clientId);
    }

    public double calcularValorPagoPelasMovimentacoes(int totalMovimentacoes) {
        if (totalMovimentacoes <= 10) {
            return totalMovimentacoes * 1.00;
        } else if (totalMovimentacoes <= 20) {
            return totalMovimentacoes * 0.75;
        } else {
            return totalMovimentacoes * 0.50;
        }
    }
}
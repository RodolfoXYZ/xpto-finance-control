package com.example.xpto_finance_control.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xpto_finance_control.models.Cliente;
import com.example.xpto_finance_control.models.Conta;
import com.example.xpto_finance_control.models.Movimentacao;
import com.example.xpto_finance_control.models.PessoaFisica;
import com.example.xpto_finance_control.models.PessoaJuridica;
import com.example.xpto_finance_control.models.TipoMovimentacao;
import com.example.xpto_finance_control.repositories.MovimentacaoRepository;
import com.example.xpto_finance_control.repositories.PessoaFisicaRepository;
import com.example.xpto_finance_control.repositories.PessoaJuridicaRepository;

@Service
public class RelatorioService {

    @Autowired
    MovimentacaoRepository movimentacaoRepository;
    @Autowired
    MovimentacaoService movimentacaoService;
    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    ContaService contaService;

    private static final DateTimeFormatter FORMATADOR_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public String calcularReceitaPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<Movimentacao> movimentacoes = movimentacaoRepository.findByDataMovimentacaoBetween(dataInicio, dataFim);
        Map<Long, List<Movimentacao>> movimentacoesPorCliente = new HashMap<>();
        for (Movimentacao movimentacao : movimentacoes) {
            Long clienteId = movimentacao.getCliente().getClienteId();
            movimentacoesPorCliente
                    .computeIfAbsent(clienteId, k -> new ArrayList<>())
                    .add(movimentacao);
        }
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(String.format("Relatório de Receitas - Período: %s a %s\n\n",
                dataInicio.format(FORMATADOR_BR), dataFim.format(FORMATADOR_BR)));
        double totalReceitas = 0.0;
        for (Map.Entry<Long, List<Movimentacao>> entry : movimentacoesPorCliente.entrySet()) {
            Long clienteId = entry.getKey();
            List<Movimentacao> movimentacoesCliente = entry.getValue();
            String clienteNome = movimentacoesCliente.get(0).getCliente().getNome();
            int quantidadeMovimentacoes = movimentacoesCliente.size();
            double somaValores = movimentacoesCliente.stream()
                    .mapToDouble(m -> m.getValor().doubleValue())
                    .sum();
            double valorPagoPelasMovimentacoes = movimentacaoService.calcularValorPagoPelasMovimentacoes(quantidadeMovimentacoes);
            totalReceitas += valorPagoPelasMovimentacoes;
            relatorio.append(String.format(
                    "Cliente: %s (ID: %d)\n" +
                            "Quantidade de Movimentações: %d\n" +
                            "Soma dos Valores: %.2f\n" +
                            "Valor Pago pelas Movimentações: %.2f\n\n",
                    clienteNome, clienteId, quantidadeMovimentacoes, somaValores, valorPagoPelasMovimentacoes
            ));
        }
        relatorio.append(String.format("Total de Receitas no Período: %.2f\n", totalReceitas));
        return relatorio.toString();
    }

    public List<String> gerarRelatorioSaldoTodosClientes(LocalDate dataReferencia) {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.findAll();
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaRepository.findAll();
        List<String> relatorio = new ArrayList<>();
        for (PessoaFisica pessoaFisica : pessoasFisicas) {
            List<Conta> contas = contaService.buscarContasPorCliente(pessoaFisica);
            BigDecimal saldoTotal = contas.stream()
                    .map(Conta::getSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            String linhaRelatorio = String.format(
                    "Cliente: %s - Cliente desde: %s - Saldo em %s: %.2f",
                    pessoaFisica.getNome(),
                    pessoaFisica.getDataCadastro().format(FORMATADOR_BR),
                    dataReferencia.format(FORMATADOR_BR),
                    saldoTotal
            );
            relatorio.add(linhaRelatorio);
        }
        for (PessoaJuridica pessoaJuridica : pessoasJuridicas) {
            List<Conta> contas = contaService.buscarContasPorCliente(pessoaJuridica);
            BigDecimal saldoTotal = contas.stream()
                    .map(Conta::getSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            String linhaRelatorio = String.format(
                    "Cliente: %s - Cliente desde: %s - Saldo em %s: %.2f",
                    pessoaJuridica.getNome(),
                    pessoaJuridica.getDataCadastro().format(FORMATADOR_BR),
                    dataReferencia.format(FORMATADOR_BR),
                    saldoTotal
            );
            relatorio.add(linhaRelatorio);
        }
        return relatorio;
    }

    public String gerarRelatorioSaldoClientePorPeriodo(Long clienteId, LocalDate dataInicio, LocalDate dataFim) {
        Cliente cliente;
        if (pessoaFisicaRepository.existsById(clienteId)) {
            cliente = pessoaFisicaRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Pessoa Física não encontrada"));
        } else if (pessoaJuridicaRepository.existsById(clienteId)) {
            cliente = pessoaJuridicaRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Pessoa Jurídica não encontrada"));
        } else {
            throw new RuntimeException("Cliente não encontrado");
        }
        List<Conta> contas = contaService.buscarContasPorCliente(cliente);
        List<Movimentacao> movimentacoes = new ArrayList<>();
        for (Conta conta : contas) {
            movimentacoes.addAll(movimentacaoRepository.findByContaAndDataMovimentacaoBetween(conta, dataInicio, dataFim));
        }
        BigDecimal saldoInicial = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.CREDITO))
                .map(Movimentacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal saldoAtual = contas.stream()
                .map(Conta::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long movimentacoesCredito = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.CREDITO))
                .count();
        long movimentacoesDebito = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.DEBITO))
                .count();
        double valorPago = movimentacaoService.calcularValorPagoPelasMovimentacoes(movimentacoes.size());
        return String.format(
                "Período: %s a %s;\n" +
                        "Cliente: %s - Cliente desde: %s;\n" +
                        "Endereço: %s;\n" +
                        "Movimentações de crédito: %d;\n" +
                        "Movimentações de débito: %d;\n" +
                        "Total de movimentações: %d;\n" +
                        "Valor pago pelas movimentações: %.2f;\n" +
                        "Saldo inicial: %.2f;\n" +
                        "Saldo atual: %.2f;",
                dataInicio.format(FORMATADOR_BR),
                dataFim.format(FORMATADOR_BR),
                cliente.getNome(),
                cliente.getDataCadastro().format(FORMATADOR_BR),
                cliente.getEndereco().toString(),
                movimentacoesCredito,
                movimentacoesDebito,
                movimentacoes.size(),
                valorPago,
                saldoInicial,
                saldoAtual
        );
    }

    public String gerarRelatorioSaldoCliente(Long clienteId) {
        Cliente cliente;
        if (pessoaFisicaRepository.existsById(clienteId)) {
            cliente = pessoaFisicaRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Pessoa Física não encontrada"));
        } else if (pessoaJuridicaRepository.existsById(clienteId)) {
            cliente = pessoaJuridicaRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Pessoa Jurídica não encontrada"));
        } else {
            throw new RuntimeException("Cliente não encontrado");
        }
        List<Conta> contas = contaService.buscarContasPorCliente(cliente);
        List<Movimentacao> movimentacoes = new ArrayList<>();
        for (Conta conta : contas) {
            movimentacoes.addAll(movimentacaoRepository.findByConta(conta));
        }
        BigDecimal saldoInicial = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.CREDITO))
                .sorted((m1, m2) -> m1.getDataMovimentacao().compareTo(m2.getDataMovimentacao()))
                .map(Movimentacao::getValor)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        BigDecimal saldoAtual = contas.stream()
                .map(Conta::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long movimentacoesCredito = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.CREDITO))
                .count();
        long movimentacoesDebito = movimentacoes.stream()
                .filter(m -> m.getTipoMovimentacao().equals(TipoMovimentacao.DEBITO))
                .count();
        double valorPago = movimentacaoService.calcularValorPagoPelasMovimentacoes(movimentacoes.size());
        return String.format(
                "Cliente: %s - Cliente desde: %s;\n" +
                        "Endereço: %s;\n" +
                        "Movimentações de crédito: %d;\n" +
                        "Movimentações de débito: %d;\n" +
                        "Total de movimentações: %d;\n" +
                        "Valor pago pelas movimentações: %.2f;\n" +
                        "Saldo inicial: %.2f;\n" +
                        "Saldo atual: %.2f;",
                cliente.getNome(),
                cliente.getDataCadastro().format(FORMATADOR_BR),
                cliente.getEndereco().toString(),
                movimentacoesCredito,
                movimentacoesDebito,
                movimentacoes.size(),
                valorPago,
                saldoInicial,
                saldoAtual
        );
    }
}
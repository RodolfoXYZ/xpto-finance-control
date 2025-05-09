package com.example.xpto_finance_control.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xpto_finance_control.services.ClienteService;
import com.example.xpto_finance_control.services.MovimentacaoService;
import com.example.xpto_finance_control.services.RelatorioService;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    RelatorioService relatorioService;

    @GetMapping("/receita-por-periodo")
    public ResponseEntity<?> calcularReceitaPorPeriodo(
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
        try {
            String relatorio = relatorioService.calcularReceitaPorPeriodo(dataInicio, dataFim);

            if (relatorio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma movimentação encontrada no período especificado.");
            }
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/saldo-todos-clientes")
    public ResponseEntity<?> gerarRelatorioSaldoTodosClientes(
            @RequestParam("dataReferencia") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataReferencia) {
        try {
            List<String> relatorio = relatorioService.gerarRelatorioSaldoTodosClientes(dataReferencia);
            if (relatorio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum saldo encontrado para os clientes.");
            }
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/saldo-cliente")
    public ResponseEntity<?> gerarRelatorioSaldoCliente(
            @RequestParam("clienteId") Long clienteId) {
        try {
            String relatorio = relatorioService.gerarRelatorioSaldoCliente(clienteId);
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    @GetMapping("/saldo-cliente-por-periodo")
    public ResponseEntity<?> gerarRelatorioSaldoClientePorPeriodo(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("dataInicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim) {
        try {
            String relatorio = relatorioService.gerarRelatorioSaldoClientePorPeriodo(clienteId, dataInicio, dataFim);
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
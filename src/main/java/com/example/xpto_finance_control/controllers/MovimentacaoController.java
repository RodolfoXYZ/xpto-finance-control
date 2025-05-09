package com.example.xpto_finance_control.controllers;

import com.example.xpto_finance_control.DTOs.MovimentacaoDTO;
import com.example.xpto_finance_control.models.Movimentacao;
import com.example.xpto_finance_control.services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacao")
public class MovimentacaoController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            movimentacaoService.cadastrarMovimentacao(movimentacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Movimentação cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar movimentação: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarMovimentacoes();
        if (movimentacoes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma movimentação encontrada.");
        }
        return ResponseEntity.ok(movimentacoes);
    }

    // @PutMapping("/atualizar/{id}")
    // public ResponseEntity<?> atualizarMovimentacao(@PathVariable Long id, @RequestBody MovimentacaoDTO movimentacaoDTO) {
    //     try {
    //         Movimentacao movimentacaoAtualizada = movimentacaoService.atualizarMovimentacao(id, movimentacaoDTO);
    //         return ResponseEntity.ok(movimentacaoAtualizada);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar movimentação: " + e.getMessage());
    //     }
    // }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarMovimentacao(@PathVariable Long id) {
        try {
            movimentacaoService.deletarMovimentacao(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Movimentação deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar movimentação: " + e.getMessage());
        }
    }
}
package com.example.xpto_finance_control.controllers;

import com.example.xpto_finance_control.DTOs.ContaDTO;
import com.example.xpto_finance_control.models.Cliente;
import com.example.xpto_finance_control.models.Conta;
import com.example.xpto_finance_control.services.ClienteService;
import com.example.xpto_finance_control.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    ContaService contaService;

    @Autowired
    ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarConta(@RequestBody ContaDTO contaDTO) {
        try {
            contaService.cadastrarConta(contaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Conta cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    @GetMapping("/ativas")
    public ResponseEntity<?> listarContasAtivas() {
        try {
            List<Conta> contasAtivas = contaService.listarContasAtivas();
            if (contasAtivas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma conta ativa encontrada.");
            }
            return ResponseEntity.ok(contasAtivas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao buscar contas ativas: " + e.getMessage());
        }
    }

    @GetMapping("/ativas/cliente/{clienteId}")
    public ResponseEntity<?> listarContasAtivasPorCliente(@PathVariable Long clienteId) {
        try {
            Cliente cliente = clienteService.buscarClientePorId(clienteId); // Método para buscar o cliente
            List<Conta> contasAtivas = contaService.listarContasAtivasPorCliente(cliente);
            if (contasAtivas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma conta ativa encontrada para o cliente especificado.");
            }
            return ResponseEntity.ok(contasAtivas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao buscar contas ativas: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarConta(@PathVariable Long id, @RequestBody ContaDTO contaDTO) {
        try {
            Conta contaAtualizada = contaService.atualizarConta(id, contaDTO);
            return ResponseEntity.ok(contaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar conta: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarConta(@PathVariable Long id) {
        try {
            contaService.deletarConta(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Conta deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar conta: " + e.getMessage());
        }
    }
}
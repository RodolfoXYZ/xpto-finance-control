package com.example.xpto_finance_control.controllers;

import com.example.xpto_finance_control.DTOs.PessoaFisicaDTO;
import com.example.xpto_finance_control.DTOs.PessoaJuridicaDTO;
import com.example.xpto_finance_control.models.PessoaFisica;
import com.example.xpto_finance_control.models.PessoaJuridica;
import com.example.xpto_finance_control.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar/pf")
    public ResponseEntity<String> cadastrarPessoaFisica(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
        try {
            clienteService.cadastrarPessoaFisica(pessoaFisicaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa Física cadastrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar Pessoa Física: " + e.getMessage());
        }
    }

    @PostMapping("/cadastrar/pj")
    public ResponseEntity<String> cadastrarPessoaJuridica(@RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
        try {
            clienteService.cadastrarPessoaJuridica(pessoaJuridicaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa Jurídica cadastrada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar Pessoa Jurídica: " + e.getMessage());
        }
    }

    @GetMapping("/listar/pf")
    public ResponseEntity<?> listarPessoasFisicas() {
        try {
            List<PessoaFisica> pessoasFisicas = clienteService.listarPessoasFisicas();
            if (pessoasFisicas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma Pessoa Física encontrada.");
            }
            return ResponseEntity.ok(pessoasFisicas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar Pessoas Físicas: " + e.getMessage());
        }
    }

    @GetMapping("/listar/pj")
    public ResponseEntity<?> listarPessoasJuridicas() {
        try {
            List<PessoaJuridica> pessoasJuridicas = clienteService.listarPessoasJuridicas();
            if (pessoasJuridicas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhuma Pessoa Jurídica encontrada.");
            }
            return ResponseEntity.ok(pessoasJuridicas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar Pessoas Jurídicas: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/pf/{id}")
    public ResponseEntity<?> atualizarPessoaFisica(@PathVariable Long id, @RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
        try {
            PessoaFisica pessoaFisica = clienteService.atualizarPessoaFisica(id, pessoaFisicaDTO);
            return ResponseEntity.ok(pessoaFisica);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar Pessoa Física: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar Pessoa Física: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/pj/{id}")
    public ResponseEntity<?> atualizarPessoaJuridica(@PathVariable Long id, @RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
        try {
            PessoaJuridica pessoaJuridica = clienteService.atualizarPessoaJuridica(id, pessoaJuridicaDTO);
            return ResponseEntity.ok(pessoaJuridica);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar Pessoa Jurídica: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar Pessoa Jurídica: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/pf/{id}")
    public ResponseEntity<String> deletarPessoaFisica(@PathVariable Long id) {
        try {
            clienteService.deletarPessoaFisica(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pessoa Física deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar Pessoa Física: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar Pessoa Física: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/pj/{id}")
    public ResponseEntity<String> deletarPessoaJuridica(@PathVariable Long id) {
        try {
            clienteService.deletarPessoaJuridica(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Pessoa Jurídica deletada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar Pessoa Jurídica: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar Pessoa Jurídica: " + e.getMessage());
        }
    }
}
package com.example.xpto_finance_control.controllers;

import com.example.xpto_finance_control.DTOs.EnderecoDTO;
import com.example.xpto_finance_control.models.Endereco;
import com.example.xpto_finance_control.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        try {
            enderecoService.cadastrarEndereco(enderecoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Endereço cadastrado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar endereço: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarEndereco() {
        List<Endereco> enderecos = enderecoService.listarEnderecos();
        if (enderecos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum endereço encontrado.");
        }
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEndereco(@PathVariable Long id, @RequestBody EnderecoDTO enderecoDTO) {
        try {
            Endereco enderecoAtualizado = enderecoService.atualizarEndereco(id, enderecoDTO);
            return ResponseEntity.ok(enderecoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEndereco(@PathVariable Long id) {
        try {
            enderecoService.deletarEndereco(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Endereço deletado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar endereço: " + e.getMessage());
        }
    }
}
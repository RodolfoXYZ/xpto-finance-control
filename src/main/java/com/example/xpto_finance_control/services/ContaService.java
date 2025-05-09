package com.example.xpto_finance_control.services;

import com.example.xpto_finance_control.DTOs.ContaDTO;
import com.example.xpto_finance_control.DTOs.ContaSimplificadaDTO;
import com.example.xpto_finance_control.models.Cliente;
import com.example.xpto_finance_control.models.Conta;
import com.example.xpto_finance_control.models.PessoaFisica;
import com.example.xpto_finance_control.models.PessoaJuridica;
import com.example.xpto_finance_control.repositories.ContaRepository;
import com.example.xpto_finance_control.repositories.PessoaFisicaRepository;
import com.example.xpto_finance_control.repositories.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Conta cadastrarConta(ContaDTO contaDTO) {
        Conta conta = new Conta();
        conta.setBanco(contaDTO.banco());
        conta.setAgencia(contaDTO.agencia());
        conta.setNumeroConta(contaDTO.numeroConta());
        PessoaFisica clienteFisica = pessoaFisicaRepository.findById(contaDTO.clienteId()).orElse(null);
        if (clienteFisica != null) {
            conta.setCliente(clienteFisica);  // Cliente Físico
        } else {
            PessoaJuridica clienteJuridica = pessoaJuridicaRepository.findById(contaDTO.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + contaDTO.clienteId()));
            conta.setCliente(clienteJuridica); // Cliente Jurídico
        }
        return contaRepository.save(conta);
    }

    public Conta cadastrarContaInicial(ContaSimplificadaDTO contaDTO, Cliente cliente) {
        Conta conta = new Conta();
        conta.setBanco(contaDTO.banco());
        conta.setAgencia(contaDTO.agencia());
        conta.setNumeroConta(contaDTO.numeroConta());
        conta.setCliente(cliente);
        return contaRepository.save(conta);
    }

    // public List<Conta> listarContas() {
    //     return contaRepository.findAll();
    // }

    public Conta atualizarConta(Long id, ContaDTO contaDTO) {
        Conta conta = contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (!conta.getMovimentacoes().isEmpty()) {
            throw new RuntimeException("Não é possível alterar os dados de uma conta que já possui movimentações registradas.");
        } else {
            conta.setBanco(contaDTO.banco());
            conta.setAgencia(contaDTO.agencia());
            conta.setNumeroConta(contaDTO.numeroConta());
            return contaRepository.save(conta);
        }
    }

    public void deletarConta(Long id) {
        String sql = "{CALL desativar_conta(?)}";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar conta: " + e.getMessage());
        }
    }

    public List<Conta> buscarContasPorCliente(Cliente cliente) {
        return contaRepository.findByCliente(cliente);
    }

    public List<Conta> listarContasAtivasPorCliente(Cliente cliente) {
        return contaRepository.findByClienteAndAtivaTrue(cliente);
    }

    public List<Conta> listarContasAtivas() {
        return contaRepository.findByAtivaTrue();
    }
}
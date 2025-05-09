package com.example.xpto_finance_control.services;

import com.example.xpto_finance_control.DTOs.PessoaFisicaDTO;
import com.example.xpto_finance_control.DTOs.PessoaJuridicaDTO;
import com.example.xpto_finance_control.models.*;
import com.example.xpto_finance_control.repositories.ContaRepository;
import com.example.xpto_finance_control.repositories.MovimentacaoRepository;
import com.example.xpto_finance_control.repositories.PessoaFisicaRepository;
import com.example.xpto_finance_control.repositories.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    ContaService contaService;

    @Autowired
    MovimentacaoService movimentacaoService;

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    @Autowired
    ContaRepository contaRepository;

    public PessoaFisica cadastrarPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO) {
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome(pessoaFisicaDTO.nome());
        Endereco endereco = enderecoService.cadastrarEndereco(pessoaFisicaDTO.endereco());
        pessoaFisica.setEndereco(endereco);
        pessoaFisica.setTelefone(pessoaFisicaDTO.telefone());
        pessoaFisica.setTipoCliente(TipoCliente.PF);
        pessoaFisica.setCpf(pessoaFisicaDTO.cpf());
        pessoaFisica.setDataCadastro(LocalDate.now());
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        Conta contaInicial = contaService.cadastrarContaInicial(pessoaFisicaDTO.contaInicial(), pessoaFisica);
        pessoaFisica.getContas().add(contaInicial);
        Movimentacao movimentacaoInicial = movimentacaoService.criarMovimentacaoInicial(contaInicial, pessoaFisicaDTO.movimentacaoInicial());
        contaInicial.getMovimentacoes().add(movimentacaoInicial);
        contaRepository.save(contaInicial);
        return pessoaFisicaRepository.save(pessoaFisica);
    }

    public PessoaJuridica cadastrarPessoaJuridica(PessoaJuridicaDTO pessoaJuridicaDTO) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome(pessoaJuridicaDTO.nome());
        Endereco endereco = enderecoService.cadastrarEndereco(pessoaJuridicaDTO.endereco());
        pessoaJuridica.setEndereco(endereco);
        pessoaJuridica.setTelefone(pessoaJuridicaDTO.telefone());
        pessoaJuridica.setTipoCliente(TipoCliente.PJ);
        pessoaJuridica.setCnpj(pessoaJuridicaDTO.cnpj());
        pessoaJuridica.setDataCadastro(LocalDate.now());
        pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
        Conta contaInicial = contaService.cadastrarContaInicial(pessoaJuridicaDTO.contaInicial(), pessoaJuridica);
        pessoaJuridica.getContas().add(contaInicial);
        Movimentacao movimentacaoInicial = movimentacaoService.criarMovimentacaoInicial(contaInicial, pessoaJuridicaDTO.movimentacaoInicial());
        contaInicial.getMovimentacoes().add(movimentacaoInicial);
        contaRepository.save(contaInicial);
        return pessoaJuridicaRepository.save(pessoaJuridica);
    }

    public List<PessoaFisica> listarPessoasFisicas() {
        return pessoaFisicaRepository.findAll();
    }

    public List<PessoaJuridica> listarPessoasJuridicas() {
        return pessoaJuridicaRepository.findAll();
    }

    public PessoaFisica atualizarPessoaFisica(Long id, PessoaFisicaDTO pessoaFisicaDTO) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa Física não encontrada"));
        Endereco endereco = enderecoService.cadastrarEndereco(pessoaFisicaDTO.endereco());
        pessoaFisica.setEndereco(endereco);
        pessoaFisica.setTelefone(pessoaFisicaDTO.telefone());
        return pessoaFisicaRepository.save(pessoaFisica);
    }

    public PessoaJuridica atualizarPessoaJuridica(Long id, PessoaJuridicaDTO pessoaJuridicaDTO) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa Jurídica não encontrada"));
        Endereco endereco = enderecoService.cadastrarEndereco(pessoaJuridicaDTO.endereco());
        pessoaJuridica.setEndereco(endereco);
        pessoaJuridica.setTelefone(pessoaJuridicaDTO.telefone());
        return pessoaJuridicaRepository.save(pessoaJuridica);
    }

    public void deletarPessoaFisica(Long id) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa Física não encontrada"));
        pessoaFisicaRepository.delete(pessoaFisica);
    }

    public void deletarPessoaJuridica(Long id) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa Jurídica não encontrada"));
        pessoaJuridicaRepository.delete(pessoaJuridica);
    }

    public Cliente buscarClientePorId(Long clienteId) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(clienteId).orElse(null);
        if (pessoaFisica != null) {
            return pessoaFisica;
        }
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
        return pessoaJuridica;
    }
}

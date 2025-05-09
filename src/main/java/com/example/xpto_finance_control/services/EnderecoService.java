package com.example.xpto_finance_control.services;

import com.example.xpto_finance_control.DTOs.EnderecoDTO;
import com.example.xpto_finance_control.models.Endereco;
import com.example.xpto_finance_control.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    public Endereco cadastrarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.rua());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setComplemento(enderecoDTO.complemento());
        endereco.setBairro(enderecoDTO.bairro());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setCep(enderecoDTO.cep());
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }

    public Endereco atualizarEndereco(Long id, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        endereco.setRua(enderecoDTO.rua());
        endereco.setNumero(enderecoDTO.numero());
        endereco.setCidade(enderecoDTO.cidade());
        endereco.setEstado(enderecoDTO.estado());
        endereco.setCep(enderecoDTO.cep());
        return enderecoRepository.save(endereco);
    }

    public void deletarEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}

package com.example.xpto_finance_control;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.xpto_finance_control.models.Conta;
import com.example.xpto_finance_control.models.Endereco;
import com.example.xpto_finance_control.models.Movimentacao;
import com.example.xpto_finance_control.models.PessoaFisica;
import com.example.xpto_finance_control.models.TipoCliente;
import com.example.xpto_finance_control.models.TipoMovimentacao;
import com.example.xpto_finance_control.repositories.ContaRepository;
import com.example.xpto_finance_control.repositories.EnderecoRepository;
import com.example.xpto_finance_control.repositories.MovimentacaoRepository;
import com.example.xpto_finance_control.repositories.PessoaFisicaRepository;
import com.example.xpto_finance_control.repositories.PessoaJuridicaRepository;

@SpringBootApplication
public class XptoFinanceControlApplication implements CommandLineRunner {
    @Autowired
    PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    PessoaJuridicaRepository pessoaJuridicaRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    ContaRepository contaRepository;
    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    public static void main(String[] args) {
        SpringApplication.run(XptoFinanceControlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        PessoaFisica rodolfo = new PessoaFisica();
        rodolfo.setClienteId(null);
        rodolfo.setNome("Rodolfo");
        rodolfo.setTipoCliente(TipoCliente.PF);
        rodolfo.setCpf("71223141462");
        rodolfo.setDataCadastro(LocalDate.now());
        rodolfo.setTelefone("(81) 99595-7825");

        Endereco casaRodolfo = new Endereco();
        casaRodolfo.setEnderecoId(null);
        casaRodolfo.setRua("Rua Alto do Brasil");
        casaRodolfo.setNumero("1601");
        casaRodolfo.setComplemento("Casa");
        casaRodolfo.setBairro("Alto Santa Terezinha");
        casaRodolfo.setCidade("Recife");
        casaRodolfo.setEstado("Pernambuco");
        casaRodolfo.setCep("52080-050");

        enderecoRepository.save(casaRodolfo);
        rodolfo.setEndereco(casaRodolfo);
        pessoaFisicaRepository.save(rodolfo);

        Conta contaRodolfo = new Conta();
        contaRodolfo.setContaId(null);
        contaRodolfo.setBanco("Banco do Brasil");
        contaRodolfo.setAgencia("1234");
        contaRodolfo.setNumeroConta("123456");
        contaRodolfo.setCliente(rodolfo);
        contaRodolfo.setSaldo(new BigDecimal("1000.00"));

        contaRepository.save(contaRodolfo);
        rodolfo.setContas(List.of(contaRodolfo));
        pessoaFisicaRepository.save(rodolfo);

        Movimentacao movimentacaoRodolfo = new Movimentacao();
        movimentacaoRodolfo.setMovimentacaoId(null);
        movimentacaoRodolfo.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoRodolfo.setCliente(rodolfo);
        movimentacaoRodolfo.setConta(contaRodolfo);
        movimentacaoRodolfo.setValor(new BigDecimal("1000.00"));
        movimentacaoRodolfo.setDataMovimentacao(LocalDate.now());

        movimentacaoRepository.save(movimentacaoRodolfo);
        contaRodolfo.setMovimentacoes(List.of(movimentacaoRodolfo));
        contaRepository.save(contaRodolfo);

        // Cliente 2
        PessoaFisica joao = new PessoaFisica();
        joao.setClienteId(null);
        joao.setNome("João Silva");
        joao.setTipoCliente(TipoCliente.PF);
        joao.setCpf("12345678901");
        joao.setDataCadastro(LocalDate.now());
        joao.setTelefone("(11) 98765-4321");

        Endereco casaJoao = new Endereco();
        casaJoao.setEnderecoId(null);
        casaJoao.setRua("Rua das Flores");
        casaJoao.setNumero("100");
        casaJoao.setComplemento("Apto 101");
        casaJoao.setBairro("Centro");
        casaJoao.setCidade("São Paulo");
        casaJoao.setEstado("SP");
        casaJoao.setCep("01000-000");

        enderecoRepository.save(casaJoao);
        joao.setEndereco(casaJoao);
        pessoaFisicaRepository.save(joao);

        Conta contaJoao = new Conta();
        contaJoao.setContaId(null);
        contaJoao.setBanco("Itaú");
        contaJoao.setAgencia("5678");
        contaJoao.setNumeroConta("987654");
        contaJoao.setCliente(joao);
        contaJoao.setSaldo(new BigDecimal("2000.00"));

        contaRepository.save(contaJoao);
        joao.setContas(List.of(contaJoao));
        pessoaFisicaRepository.save(joao);

        Movimentacao movimentacaoJoao = new Movimentacao();
        movimentacaoJoao.setMovimentacaoId(null);
        movimentacaoJoao.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoJoao.setCliente(joao);
        movimentacaoJoao.setConta(contaJoao);
        movimentacaoJoao.setValor(new BigDecimal("2000.00"));
        movimentacaoJoao.setDataMovimentacao(LocalDate.now());

        movimentacaoRepository.save(movimentacaoJoao);
        contaJoao.setMovimentacoes(List.of(movimentacaoJoao));
        contaRepository.save(contaJoao);

        // Cliente 3
        PessoaFisica maria = new PessoaFisica();
        maria.setClienteId(null);
        maria.setNome("Maria Oliveira");
        maria.setTipoCliente(TipoCliente.PF);
        maria.setCpf("98765432100");
        maria.setDataCadastro(LocalDate.now());
        maria.setTelefone("(21) 99999-8888");

        Endereco casaMaria = new Endereco();
        casaMaria.setEnderecoId(null);
        casaMaria.setRua("Avenida Brasil");
        casaMaria.setNumero("500");
        casaMaria.setComplemento("Casa");
        casaMaria.setBairro("Jardim América");
        casaMaria.setCidade("Rio de Janeiro");
        casaMaria.setEstado("RJ");
        casaMaria.setCep("20000-000");

        enderecoRepository.save(casaMaria);
        maria.setEndereco(casaMaria);
        pessoaFisicaRepository.save(maria);

        Conta contaMaria = new Conta();
        contaMaria.setContaId(null);
        contaMaria.setBanco("Bradesco");
        contaMaria.setAgencia("4321");
        contaMaria.setNumeroConta("654321");
        contaMaria.setCliente(maria);
        contaMaria.setSaldo(new BigDecimal("3000.00"));

        contaRepository.save(contaMaria);
        maria.setContas(List.of(contaMaria));
        pessoaFisicaRepository.save(maria);

        Movimentacao movimentacaoMaria = new Movimentacao();
        movimentacaoMaria.setMovimentacaoId(null);
        movimentacaoMaria.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoMaria.setCliente(maria);
        movimentacaoMaria.setConta(contaMaria);
        movimentacaoMaria.setValor(new BigDecimal("3000.00"));
        movimentacaoMaria.setDataMovimentacao(LocalDate.now());

        movimentacaoRepository.save(movimentacaoMaria);
        contaMaria.setMovimentacoes(List.of(movimentacaoMaria));
        contaRepository.save(contaMaria);

        PessoaFisica carlos = new PessoaFisica();
        carlos.setClienteId(null);
        carlos.setNome("Carlos Pereira");
        carlos.setTipoCliente(TipoCliente.PF);
        carlos.setCpf("45678912300");
        carlos.setDataCadastro(LocalDate.now());
        carlos.setTelefone("(31) 91234-5678");

        Endereco casaCarlos = new Endereco();
        casaCarlos.setEnderecoId(null);
        casaCarlos.setRua("Rua Minas Gerais");
        casaCarlos.setNumero("250");
        casaCarlos.setComplemento("Apto 202");
        casaCarlos.setBairro("Savassi");
        casaCarlos.setCidade("Belo Horizonte");
        casaCarlos.setEstado("MG");
        casaCarlos.setCep("30130-000");

        enderecoRepository.save(casaCarlos);
        carlos.setEndereco(casaCarlos);
        pessoaFisicaRepository.save(carlos);

        Conta contaCarlos = new Conta();
        contaCarlos.setContaId(null);
        contaCarlos.setBanco("Santander");
        contaCarlos.setAgencia("7890");
        contaCarlos.setNumeroConta("123789");
        contaCarlos.setCliente(carlos);
        contaCarlos.setSaldo(new BigDecimal("4000.00"));

        contaRepository.save(contaCarlos);
        carlos.setContas(List.of(contaCarlos));
        pessoaFisicaRepository.save(carlos);

        Movimentacao movimentacaoCarlos = new Movimentacao();
        movimentacaoCarlos.setMovimentacaoId(null);
        movimentacaoCarlos.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoCarlos.setCliente(carlos);
        movimentacaoCarlos.setConta(contaCarlos);
        movimentacaoCarlos.setValor(new BigDecimal("4000.00"));
        movimentacaoCarlos.setDataMovimentacao(LocalDate.now());

        movimentacaoRepository.save(movimentacaoCarlos);
        contaCarlos.setMovimentacoes(List.of(movimentacaoCarlos));
        contaRepository.save(contaCarlos);

// Cliente 5
        PessoaFisica ana = new PessoaFisica();
        ana.setClienteId(null);
        ana.setNome("Ana Costa");
        ana.setTipoCliente(TipoCliente.PF);
        ana.setCpf("32165498700");
        ana.setDataCadastro(LocalDate.now());
        ana.setTelefone("(41) 99876-5432");

        Endereco casaAna = new Endereco();
        casaAna.setEnderecoId(null);
        casaAna.setRua("Rua Curitiba");
        casaAna.setNumero("800");
        casaAna.setComplemento("Casa");
        casaAna.setBairro("Centro");
        casaAna.setCidade("Curitiba");
        casaAna.setEstado("PR");
        casaAna.setCep("80010-000");

        enderecoRepository.save(casaAna);
        ana.setEndereco(casaAna);
        pessoaFisicaRepository.save(ana);

        Conta contaAna = new Conta();
        contaAna.setContaId(null);
        contaAna.setBanco("Caixa Econômica Federal");
        contaAna.setAgencia("4567");
        contaAna.setNumeroConta("987321");
        contaAna.setCliente(ana);
        contaAna.setSaldo(new BigDecimal("5000.00"));

        contaRepository.save(contaAna);
        ana.setContas(List.of(contaAna));
        pessoaFisicaRepository.save(ana);

        Movimentacao movimentacaoAna = new Movimentacao();
        movimentacaoAna.setMovimentacaoId(null);
        movimentacaoAna.setTipoMovimentacao(TipoMovimentacao.CREDITO);
        movimentacaoAna.setCliente(ana);
        movimentacaoAna.setConta(contaAna);
        movimentacaoAna.setValor(new BigDecimal("5000.00"));
        movimentacaoAna.setDataMovimentacao(LocalDate.now());

        movimentacaoRepository.save(movimentacaoAna);
        contaAna.setMovimentacoes(List.of(movimentacaoAna));
        contaRepository.save(contaAna);
    }
}
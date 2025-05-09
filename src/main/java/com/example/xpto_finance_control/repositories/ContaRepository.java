package com.example.xpto_finance_control.repositories;

import com.example.xpto_finance_control.models.Cliente;
import com.example.xpto_finance_control.models.Conta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    List<Conta> findByCliente(Cliente cliente);
    List<Conta> findByClienteAndAtivaTrue(Cliente cliente);
    List<Conta> findByAtivaTrue();
}

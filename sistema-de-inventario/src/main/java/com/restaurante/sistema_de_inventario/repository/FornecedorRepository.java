package com.restaurante.sistema_de_inventario.repository;

import com.restaurante.sistema_de_inventario.entity.Fornecedor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {

    List<Fornecedor> findByNomeContainingIgnoreCase(String nome);
    boolean existsByCnpj(String cnpj);
}
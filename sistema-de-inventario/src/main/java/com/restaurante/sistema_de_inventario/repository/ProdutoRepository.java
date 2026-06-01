package com.restaurante.sistema_de_inventario.repository;

import com.restaurante.sistema_de_inventario.entity.Produto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCategoriaId(Long categoriaId);
    boolean existsByNomeIgnoreCase(String nome);
}
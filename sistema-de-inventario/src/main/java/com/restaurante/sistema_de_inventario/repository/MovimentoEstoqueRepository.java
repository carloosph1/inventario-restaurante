package com.restaurante.sistema_de_inventario.repository;

import com.restaurante.sistema_de_inventario.entity.MovimentoEstoque;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentoEstoqueRepository extends CrudRepository<MovimentoEstoque, Long> {

    List<MovimentoEstoque> findByProdutoId(Long produtoId);
    List<MovimentoEstoque> findByTipo(String tipo);
}
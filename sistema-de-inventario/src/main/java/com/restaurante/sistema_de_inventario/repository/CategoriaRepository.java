package com.restaurante.sistema_de_inventario.repository;

import com.restaurante.sistema_de_inventario.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
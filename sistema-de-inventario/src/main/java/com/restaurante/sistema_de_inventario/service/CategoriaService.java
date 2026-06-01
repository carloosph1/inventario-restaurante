package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Categoria;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    @Transactional(readOnly = true)
    public Iterable<Categoria> listarTodas() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        if (repository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome");
        }
        return repository.save(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Fornecedor;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;

    @Transactional(readOnly = true)
    public Iterable<Fornecedor> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Fornecedor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor", id));
    }

    @Transactional
    public Fornecedor salvar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj() != null &&
                repository.existsByCnpj(fornecedor.getCnpj())) {
            throw new IllegalArgumentException("Já existe um fornecedor com esse CNPJ");
        }
        return repository.save(fornecedor);
    }

    @Transactional
    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}

package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Produto;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    @Transactional(readOnly = true)
    public Iterable<Produto> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", id));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        if (repository.existsByNomeIgnoreCase(produto.getNome())) {
            throw new IllegalArgumentException("Já existe um produto com esse nome");
        }
        return repository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto dados) {
        Produto atual = buscarPorId(id);
        atual.setNome(dados.getNome());
        atual.setQuantidade(dados.getQuantidade());
        atual.setUnidade(dados.getUnidade());
        atual.setPreco(dados.getPreco());
        atual.setCategoria(dados.getCategoria());
        atual.setFornecedor(dados.getFornecedor());
        return repository.save(atual);
    }

    @Transactional
    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
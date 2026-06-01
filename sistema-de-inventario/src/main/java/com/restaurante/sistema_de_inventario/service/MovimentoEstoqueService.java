package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.MovimentoEstoque;
import com.restaurante.sistema_de_inventario.entity.Produto;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.MovimentoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovimentoEstoqueService {

    private final MovimentoEstoqueRepository repository;
    private final ProdutoService produtoService;

    @Transactional(readOnly = true)
    public Iterable<MovimentoEstoque> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public MovimentoEstoque registrar(MovimentoEstoque movimento) {
        String tipo = movimento.getTipo();
        if (!tipo.equals("ENTRADA") && !tipo.equals("SAIDA")) {
            throw new IllegalArgumentException("Tipo deve ser ENTRADA ou SAIDA");
        }

        Produto produto = produtoService.buscarPorId(
                movimento.getProduto().getId());

        if (tipo.equals("SAIDA")) {
            if (produto.getQuantidade().compareTo(movimento.getQuantidade()) < 0) {
                throw new IllegalArgumentException("Estoque insuficiente");
            }
            produto.setQuantidade(
                    produto.getQuantidade().subtract(movimento.getQuantidade()));
        } else {
            produto.setQuantidade(
                    produto.getQuantidade().add(movimento.getQuantidade()));
        }

        movimento.setProduto(produto);
        return repository.save(movimento);
    }
}
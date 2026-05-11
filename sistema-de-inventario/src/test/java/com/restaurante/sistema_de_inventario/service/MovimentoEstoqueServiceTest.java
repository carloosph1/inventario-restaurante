package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.MovimentoEstoque;
import com.restaurante.sistema_de_inventario.entity.Produto;
import com.restaurante.sistema_de_inventario.repository.MovimentoEstoqueRepository;
import com.restaurante.sistema_de_inventario.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimentoEstoqueServiceTest {

    @Mock
    private MovimentoEstoqueRepository repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveLancarExcecaoParaTipoInvalido() {
        MovimentoEstoqueService movimentoService =
                new MovimentoEstoqueService(repository, produtoService);

        Produto p = new Produto();
        p.setId(1L);
        p.setQuantidade(BigDecimal.TEN);

        MovimentoEstoque m = new MovimentoEstoque();
        m.setTipo("INVALIDO");
        m.setQuantidade(BigDecimal.ONE);
        m.setProduto(p);

        assertThrows(IllegalArgumentException.class,
                () -> movimentoService.registrar(m));
    }

    @Test
    void deveLancarExcecaoParaEstoqueInsuficiente() {
        MovimentoEstoqueService movimentoService =
                new MovimentoEstoqueService(repository, produtoService);

        Produto p = new Produto();
        p.setId(1L);
        p.setQuantidade(new BigDecimal("2.0"));

        MovimentoEstoque m = new MovimentoEstoque();
        m.setTipo("SAIDA");
        m.setQuantidade(new BigDecimal("10.0"));
        m.setProduto(p);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(p));

        assertThrows(IllegalArgumentException.class,
                () -> movimentoService.registrar(m));
    }

    @Test
    void deveRegistrarEntradaComSucesso() {
        MovimentoEstoqueService movimentoService =
                new MovimentoEstoqueService(repository, produtoService);

        Produto p = new Produto();
        p.setId(1L);
        p.setQuantidade(new BigDecimal("5.0"));

        MovimentoEstoque m = new MovimentoEstoque();
        m.setTipo("ENTRADA");
        m.setQuantidade(new BigDecimal("3.0"));
        m.setProduto(p);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(repository.save(any())).thenReturn(m);

        MovimentoEstoque salvo = movimentoService.registrar(m);

        assertNotNull(salvo);
        assertEquals(new BigDecimal("8.0"), p.getQuantidade());
    }
}
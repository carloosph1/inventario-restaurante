package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Produto;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
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
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveLancarExcecaoQuandoNomeDuplicado() {
        Produto produto = new Produto();
        produto.setNome("Queijo");
        produto.setQuantidade(BigDecimal.TEN);
        produto.setUnidade("kg");

        when(repository.existsByNomeIgnoreCase("Queijo")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.salvar(produto));
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        Produto produto = new Produto();
        produto.setNome("Leite");
        produto.setQuantidade(BigDecimal.TEN);
        produto.setUnidade("L");

        when(repository.existsByNomeIgnoreCase("Leite")).thenReturn(false);
        when(repository.save(produto)).thenReturn(produto);

        Produto salvo = service.salvar(produto);

        assertNotNull(salvo);
        assertEquals("Leite", salvo.getNome());
    }
}
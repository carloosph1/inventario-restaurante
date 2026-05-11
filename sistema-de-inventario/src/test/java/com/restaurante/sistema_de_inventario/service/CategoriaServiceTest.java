package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Categoria;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveLancarExcecaoQuandoNomeDuplicado() {
        Categoria categoria = new Categoria();
        categoria.setNome("Laticínios");

        when(repository.existsByNomeIgnoreCase("Laticínios")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.salvar(categoria));
    }

    @Test
    void deveSalvarCategoriaComSucesso() {
        Categoria categoria = new Categoria();
        categoria.setNome("Carnes");

        when(repository.existsByNomeIgnoreCase("Carnes")).thenReturn(false);
        when(repository.save(categoria)).thenReturn(categoria);

        Categoria salva = service.salvar(categoria);

        assertNotNull(salva);
        assertEquals("Carnes", salva.getNome());
    }
}
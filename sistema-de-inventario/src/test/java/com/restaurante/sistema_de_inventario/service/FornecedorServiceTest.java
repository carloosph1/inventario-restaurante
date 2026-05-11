package com.restaurante.sistema_de_inventario.service;

import com.restaurante.sistema_de_inventario.entity.Fornecedor;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.repository.FornecedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository repository;

    @InjectMocks
    private FornecedorService service;

    @Test
    void deveLancarExcecaoQuandoFornecedorNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveLancarExcecaoQuandoCnpjDuplicado() {
        Fornecedor f = new Fornecedor();
        f.setNome("Fornecedor X");
        f.setCnpj("12.345.678/0001-99");

        when(repository.existsByCnpj("12.345.678/0001-99")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.salvar(f));
    }

    @Test
    void deveSalvarFornecedorComSucesso() {
        Fornecedor f = new Fornecedor();
        f.setNome("Fornecedor Y");
        f.setCnpj("98.765.432/0001-11");

        when(repository.existsByCnpj("98.765.432/0001-11")).thenReturn(false);
        when(repository.save(f)).thenReturn(f);

        Fornecedor salvo = service.salvar(f);

        assertNotNull(salvo);
        assertEquals("Fornecedor Y", salvo.getNome());
    }

    @Test
    void deveDeletarFornecedorComSucesso() {
        Fornecedor f = new Fornecedor();
        f.setId(1L);
        f.setNome("Fornecedor Z");

        when(repository.findById(1L)).thenReturn(Optional.of(f));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}
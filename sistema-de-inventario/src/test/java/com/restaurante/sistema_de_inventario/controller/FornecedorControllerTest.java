package com.restaurante.sistema_de_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.sistema_de_inventario.entity.Fornecedor;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.service.FornecedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FornecedorController.class)
class FornecedorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FornecedorService service;

    @Test
    void deveListarFornecedores() throws Exception {
        Fornecedor f = new Fornecedor();
        f.setId(1L);
        f.setNome("Distribuidora Norte");

        when(service.listarTodos()).thenReturn(List.of(f));

        mvc.perform(get("/api/v1/fornecedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Distribuidora Norte"));
    }

    @Test
    void deveRetornar404QuandoFornecedorNaoExiste() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("Fornecedor", 99L));

        mvc.perform(get("/api/v1/fornecedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarFornecedorComSucesso() throws Exception {
        Fornecedor f = new Fornecedor();
        f.setId(1L);
        f.setNome("Fornecedor ABC");
        f.setCnpj("12.345.678/0001-99");

        when(service.salvar(any())).thenReturn(f);

        mvc.perform(post("/api/v1/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(f)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Fornecedor ABC"));
    }

    @Test
    void deveDeletarFornecedorComSucesso() throws Exception {
        doNothing().when(service).deletar(1L);

        mvc.perform(delete("/api/v1/fornecedores/1"))
                .andExpect(status().isNoContent());
    }
}
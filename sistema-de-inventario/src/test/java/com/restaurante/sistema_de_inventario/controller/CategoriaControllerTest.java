package com.restaurante.sistema_de_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.sistema_de_inventario.entity.Categoria;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.service.CategoriaService;
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

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoriaService service;

    @Test
    void deveListarCategorias() throws Exception {
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNome("Laticínios");

        when(service.listarTodas()).thenReturn(List.of(c));

        mvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Laticínios"));
    }

    @Test
    void deveBuscarCategoriaPorId() throws Exception {
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNome("Carnes");

        when(service.buscarPorId(1L)).thenReturn(c);

        mvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carnes"));
    }

    @Test
    void deveRetornar404QuandoCategoriaNaoExiste() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("Categoria", 99L));

        mvc.perform(get("/api/v1/categorias/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarCategoriaComSucesso() throws Exception {
        Categoria c = new Categoria();
        c.setId(1L);
        c.setNome("Bebidas");

        when(service.salvar(any())).thenReturn(c);

        mvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Bebidas"));
    }

    @Test
    void deveDeletarCategoriaComSucesso() throws Exception {
        doNothing().when(service).deletar(1L);

        mvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }
}
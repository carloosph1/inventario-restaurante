package com.restaurante.sistema_de_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.sistema_de_inventario.entity.Produto;
import com.restaurante.sistema_de_inventario.exception.ResourceNotFoundException;
import com.restaurante.sistema_de_inventario.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService service;

    @Test
    void deveListarProdutos() throws Exception {
        Produto p = new Produto();
        p.setId(1L);
        p.setNome("Queijo");
        p.setQuantidade(BigDecimal.TEN);
        p.setUnidade("kg");

        when(service.listarTodos()).thenReturn(List.of(p));

        mvc.perform(get("/api/v1/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Queijo"));
    }

    @Test
    void deveRetornar404QuandoProdutoNaoExiste() throws Exception {
        when(service.buscarPorId(99L))
                .thenThrow(new ResourceNotFoundException("Produto", 99L));

        mvc.perform(get("/api/v1/produtos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarProdutoComSucesso() throws Exception {
        Produto p = new Produto();
        p.setId(1L);
        p.setNome("Leite");
        p.setQuantidade(BigDecimal.TEN);
        p.setUnidade("L");

        when(service.salvar(any())).thenReturn(p);

        mvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Leite"));
    }

    @Test
    void deveRetornar400QuandoDadosInvalidos() throws Exception {
        Produto p = new Produto();
        // nome em branco — deve falhar na validação

        mvc.perform(post("/api/v1/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isBadRequest());
    }
}
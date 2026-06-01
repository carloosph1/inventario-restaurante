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
/*deve listar todas as categorias

  Estar se o endpoint que lista as categorias do restaurante
  está funcionando corretamente, simulando uma requisição HTTP
  sem precisar ligar o servidor de verdade

  * */
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
/*deve buscar por id

serve para garantir que,
quando alguém pedir os detalhes da categoria número 1,
o sistema retorne os dados corretos sem quebrar.

*/

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
/*

tentar buscar uma categoria com um ID que não existe no inventário do restaurante,
o sistema responda corretamente com o status HTTP 404 Not Found
em vez de dar erro 500 (Erro Interno) ou travar o sistema.

 */
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
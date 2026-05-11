package com.restaurante.sistema_de_inventario.controller;

import com.restaurante.sistema_de_inventario.entity.MovimentoEstoque;
import com.restaurante.sistema_de_inventario.service.MovimentoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movimentos")
@RequiredArgsConstructor
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService service;

    @GetMapping
    public ResponseEntity<Iterable<MovimentoEstoque>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<MovimentoEstoque> registrar(
            @Valid @RequestBody MovimentoEstoque movimento) {
        return ResponseEntity.status(201).body(service.registrar(movimento));
    }
}
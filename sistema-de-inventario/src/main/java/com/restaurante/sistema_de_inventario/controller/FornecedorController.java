package com.restaurante.sistema_de_inventario.controller;

import com.restaurante.sistema_de_inventario.entity.Fornecedor;
import com.restaurante.sistema_de_inventario.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService service;

    @GetMapping
    public ResponseEntity<Iterable<Fornecedor>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.status(201).body(service.salvar(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
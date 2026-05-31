package com.restaurante.sistema_de_inventario.exception;

import java.time.LocalDateTime;



//classe especial para carregar dados (chamada de DTO ou Value Object)
/* vizualização do erro
{
        "status": 404,
        "message": "Produto não encontrado no inventário.",
        "timestamp": "2026-05-30T19:27:00"
        }*/

public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, LocalDateTime.now());
    }
}
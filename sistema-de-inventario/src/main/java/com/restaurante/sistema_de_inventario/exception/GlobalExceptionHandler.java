package com.restaurante.sistema_de_inventario.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
//Captura um erro
//tentar buscar um produto com um ID que não existe no banco do restaurante
//Service vai lançar esse erro

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ErrorResponse(404, ex.getMessage()));
    }
/*
Captura as falhas do Bean Validation @NotBlank ou @Min que colocou nas entidades
o stream() do Java para varrer todos os campos que falharam na validação.
tentar cadastrar um produto sem nome e com preço negativo ao mesmo tempo, ele junta os erros usando uma barra |
*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(" | "));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, msg));
    }


    /*
            Captura a exceção nativa do Java IllegalArgumentException.
            Esse metodo pega o texto e devolve como um erro 400 (Bad Request) estruturado.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, ex.getMessage()));
    }
}
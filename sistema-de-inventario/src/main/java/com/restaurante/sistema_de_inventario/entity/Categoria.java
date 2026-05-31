package com.restaurante.sistema_de_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //name
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;
    //descricao
    @Size(max = 255)
    @Column(length = 255)
    private String descricao;
}
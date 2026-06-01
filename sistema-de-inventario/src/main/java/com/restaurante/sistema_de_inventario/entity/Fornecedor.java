package com.restaurante.sistema_de_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fornecedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //name
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;
    //telefone
    @Size(max = 20)
    @Column(length = 20)
    private String telefone;
    //cnpj
    @Size(max = 18)
    @Column(length = 18)
    private String cnpj;
}
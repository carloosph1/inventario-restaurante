package com.restaurante.sistema_de_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.0", message = "Quantidade não pode ser negativa")
    @Column(nullable = false)
    private BigDecimal quantidade;

    @NotBlank(message = "Unidade é obrigatória")
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String unidade;

    @DecimalMin(value = "0.01", message = "Preço deve ser positivo")
    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
}
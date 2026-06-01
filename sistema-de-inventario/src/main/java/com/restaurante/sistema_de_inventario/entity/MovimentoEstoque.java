package com.restaurante.sistema_de_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentos_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoque {

    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
    @NotNull(message = "Produto é obrigatório")
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
//tipo
    @NotBlank(message = "Tipo é obrigatório")
    @Column(nullable = false, length = 10)
    private String tipo; // ENTRADA ou SAIDA
//quantidade
    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser positiva")
    @Column(nullable = false)
    private BigDecimal quantidade;
//observacao
    @Size(max = 255)
    private String observacao;
//data da movimentacao
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataMovimento;
}
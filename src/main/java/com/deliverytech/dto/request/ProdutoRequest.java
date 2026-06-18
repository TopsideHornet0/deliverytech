package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    @NotBlank(message = "O nome do produto é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome do produto deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "A categoria do produto é obrigatória.")
    @Size(min = 3, max = 30, message = "A categoria deve ter entre 3 e 30 caracteres.")
    private String categoria;

    @NotBlank(message = "A descrição do produto é obrigatória.")
    @Size(min = 10, max = 300, message = "A descrição deve ter entre 10 e 300 caracteres.")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(
        value = "0.10",
        message = "O preço mínimo permitido é R$ 0,10."
    )
    @DecimalMax(
        value = "500.00",
        message = "O preço máximo permitido é R$ 500,00."
    )
    private BigDecimal preco;

    @NotNull(message = "O restaurante responsável pelo produto deve ser informado.")
    private Long restauranteId;
}
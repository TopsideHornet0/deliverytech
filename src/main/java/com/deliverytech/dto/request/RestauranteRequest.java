package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório.")
    @Size(min = 3, max = 50, message = "O nome do restaurante deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "A categoria do restaurante é obrigatória.")
    @Size(min = 3, max = 30, message = "A categoria deve ter entre 3 e 30 caracteres.")
    private String categoria;

    @NotBlank(message = "O telefone é obrigatório para contato com o restaurante.")
    @Pattern(
        regexp = "^\\d{10,11}$",
        message = "Informe um telefone válido contendo apenas números."
    )
    private String telefone;

    @NotNull(message = "A taxa de entrega deve ser informada.")
    @DecimalMin(
        value = "0.0",
        inclusive = true,
        message = "A taxa de entrega não pode ser negativa."
    )
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega deve ser informado.")
    @Min(
        value = 10,
        message = "O tempo mínimo de entrega é de 10 minutos."
    )
    @Max(
        value = 120,
        message = "O tempo máximo de entrega é de 120 minutos."
    )
    private Integer tempoEntregaMinutos;
}
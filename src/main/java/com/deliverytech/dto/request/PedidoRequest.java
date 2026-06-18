package com.deliverytech.dto.request;

import com.deliverytech.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    @NotNull(message = "O cliente responsável pelo pedido deve ser informado.")
    private Long clienteId;

    @NotNull(message = "O restaurante do pedido deve ser informado.")
    private Long restauranteId;

    @NotNull(message = "O endereço de entrega é obrigatório.")
    @Valid
    private Endereco enderecoEntrega;

    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    @Valid
    private List<ItemPedidoRequest> itens;
}
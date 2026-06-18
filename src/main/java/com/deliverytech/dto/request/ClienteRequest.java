package com.deliverytech.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank(message = "O nome é obrigatório, Sr(a).")
    @Size(
        min = 3,
        max = 30,
        message = "O nome deve ter entre 3 e 30 caracteres."
    )
    private String nome;

    @NotBlank(
        message = "O endereço de email é obrigatório para concluir o cadastro."
    )
    @Email(
        message = "O endereço informado não é válido. Exemplo: nome@provedor.com"
    )
    private String email;
}
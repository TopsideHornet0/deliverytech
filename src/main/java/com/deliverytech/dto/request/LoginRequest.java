package com.deliverytech.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Informe seu email para continuar.")
    @Email(message = "O email informado não possui um formato válido. Exemplo: nome@provedor.com")
    private String email;

    @NotBlank(message = "A senha é obrigatória para realizar o login.")
    private String senha;
}
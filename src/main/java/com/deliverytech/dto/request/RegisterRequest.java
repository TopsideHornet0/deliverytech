package com.deliverytech.dto.request;

import com.deliverytech.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "O email é obrigatório para criar uma conta.")
    @Email(message = "Informe um email válido. Exemplo: nome@provedor.com")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(
        min = 6,
        max = 50,
        message = "A senha deve possuir entre 6 e 50 caracteres."
    )
    private String senha;

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(
        min = 3,
        max = 30,
        message = "O nome deve ter entre 3 e 30 caracteres."
    )
    private String nome;

    private Role role;

    private Long restauranteId;
}
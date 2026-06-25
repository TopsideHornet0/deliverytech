package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveCriarClienteComSucesso() throws Exception {

        String json = """
        {
            "nome":"Altair Ibn-La'Ahad",
            "email":"altair.ibn.lahad@assassins.com"
        }
        """;

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void naoDeveCriarClienteComEmailEmBranco() throws Exception {

        String json = """
        {
            "nome":"Connor Kenway",
            "email":""
        }
        """;

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/clientes"))
                .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void naoDeveCriarClienteComEmailInvalido() throws Exception {

        String json = """
        {
            "nome":"Haytham Kenway",
            "email":"email-invalido"
        }
        """;

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/clientes"))
                .andExpect(jsonPath("$.details.email").exists());
    }
}
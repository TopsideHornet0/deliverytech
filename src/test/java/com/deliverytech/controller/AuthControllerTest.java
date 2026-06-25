package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRegistrarUsuarioClienteComSucesso() throws Exception {

        String json = """
        {
            "email":"connor.kenway@assassins.com",
            "senha":"123456",
            "nome":"Connor Kenway",
            "role":"CLIENTE",
            "restauranteId":0
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveRegistrarUsuarioComEmailInvalido() throws Exception {

        String json = """
        {
            "email":"email-invalido",
            "senha":"123456",
            "nome":"Haytham Kenway",
            "role":"CLIENTE",
            "restauranteId":0
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    void naoDeveRegistrarUsuarioComSenhaCurta() throws Exception {

        String json = """
        {
            "email":"haytham.kenway@templarios.com",
            "senha":"123",
            "nome":"Haytham Kenway",
            "role":"CLIENTE",
            "restauranteId":0
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andExpect(jsonPath("$.details.senha").exists());
    }

    @Test
    void deveFazerLoginComUsuarioRegistradoNoTeste() throws Exception {

        String registroJson = """
        {
            "email":"edward.kenway@assassins.com",
            "senha":"123456",
            "nome":"Edward Kenway",
            "role":"CLIENTE",
            "restauranteId":0
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk());

        String loginJson = """
        {
            "email":"edward.kenway@assassins.com",
            "senha":"123456"
        }
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk());
    }

    @Test
    void naoDeveRegistrarEmailDuplicado() throws Exception {

        String json = """
        {
            "email":"ezio.auditore@assassins.com",
            "senha":"123456",
            "nome":"Ezio Auditore",
            "role":"CLIENTE",
            "restauranteId":0
        }
        """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflito de dados"))
                .andExpect(jsonPath("$.message").value("Email já cadastrado"))
                .andExpect(jsonPath("$.path").value("/api/auth/register"));
    }
}
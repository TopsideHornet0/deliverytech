package com.deliverytech.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isBadRequest());
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
}
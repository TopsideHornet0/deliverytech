package com.deliverytech.security;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornar403QuandoUsuarioNaoEstiverAutenticado() throws Exception {

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "gabriela", roles = {"CLIENTE"})
    void clienteAutenticadoDeveConseguirAcessarClientes() throws Exception {

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "gabriela", roles = {"CLIENTE"})
    void clienteNaoDeveConseguirAcessarRestaurantes() throws Exception {

        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "joao", roles = {"ADMIN"})
    void adminDeveConseguirAcessarRestaurantes() throws Exception {

        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk());

    }

}
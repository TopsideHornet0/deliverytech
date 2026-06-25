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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveCriarRestauranteComSucesso() throws Exception {

        String json = """
        {
            "nome":"Taverna Davenport",
            "categoria":"Comida Colonial",
            "telefone":"11999990001",
            "taxaEntrega":7.50,
            "tempoEntregaMinutos":45
        }
        """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void naoDeveCriarRestauranteComNomeEmBranco() throws Exception {

        String json = """
        {
            "nome":"",
            "categoria":"Comida Colonial",
            "telefone":"11999990001",
            "taxaEntrega":7.50,
            "tempoEntregaMinutos":45
        }
        """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void naoDeveCriarRestauranteComCategoriaEmBranco() throws Exception {

        String json = """
        {
            "nome":"Taverna Kenway",
            "categoria":"",
            "telefone":"11999990002",
            "taxaEntrega":8.00,
            "tempoEntregaMinutos":50
        }
        """;

        mockMvc.perform(post("/api/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
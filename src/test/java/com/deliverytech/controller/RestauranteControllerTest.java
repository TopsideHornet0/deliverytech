package com.deliverytech.controller;

import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.RestauranteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestauranteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Taverna Davenport"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveListarRestaurantes() throws Exception {
        restauranteRepository.save(Restaurante.builder()
                .nome("Estalagem Auditore")
                .categoria("Italiana")
                .telefone("11999990004")
                .taxaEntrega(BigDecimal.valueOf(5.50))
                .tempoEntregaMinutos(30)
                .ativo(true)
                .build());

        mockMvc.perform(get("/api/restaurantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveBuscarRestaurantePorId() throws Exception {
        Restaurante restaurante = restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Kenway")
                .categoria("Comida Colonial")
                .telefone("11999990005")
                .taxaEntrega(BigDecimal.valueOf(8.00))
                .tempoEntregaMinutos(50)
                .ativo(true)
                .build());

        mockMvc.perform(get("/api/restaurantes/" + restaurante.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurante.getId()))
                .andExpect(jsonPath("$.nome").value("Taverna Kenway"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveBuscarRestaurantePorCategoria() throws Exception {
        restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Connor")
                .categoria("Comida Colonial")
                .telefone("11999990003")
                .taxaEntrega(BigDecimal.valueOf(6.50))
                .tempoEntregaMinutos(35)
                .ativo(true)
                .build());

        mockMvc.perform(get("/api/restaurantes/categoria/Comida Colonial"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].categoria").value("Comida Colonial"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deveAtualizarRestaurante() throws Exception {
        Restaurante restaurante = restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Antiga")
                .categoria("Comida Colonial")
                .telefone("11999990006")
                .taxaEntrega(BigDecimal.valueOf(6.00))
                .tempoEntregaMinutos(40)
                .ativo(true)
                .build());

        String json = """
        {
            "nome":"Taverna Atualizada",
            "categoria":"Comida Colonial Premium",
            "telefone":"11999990007",
            "taxaEntrega":9.90,
            "tempoEntregaMinutos":25
        }
        """;

        mockMvc.perform(put("/api/restaurantes/" + restaurante.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurante.getId()))
                .andExpect(jsonPath("$.nome").value("Taverna Atualizada"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void naoDeveBuscarRestauranteInexistentePorId() throws Exception {
        mockMvc.perform(get("/api/restaurantes/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.path").value("/api/restaurantes/99999"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.nome").exists());
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.categoria").exists());
    }
}
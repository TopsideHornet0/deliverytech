package com.deliverytech.controller;

import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.ProdutoRepository;
import com.deliverytech.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Restaurante restaurante;

    @BeforeEach
    void prepararDados() {
        restaurante = restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Davenport")
                .categoria("Comida Colonial")
                .telefone("11999990001")
                .taxaEntrega(BigDecimal.valueOf(7.50))
                .tempoEntregaMinutos(45)
                .ativo(true)
                .build());
    }

    @Test
    @WithMockUser(username = "haytham", roles = {"ADMIN"})
    void deveCriarProdutoComSucesso() throws Exception {

        String json = """
        {
            "nome":"Ensopado Colonial Kenway",
            "categoria":"Prato Principal",
            "descricao":"Ensopado quente servido na taverna dos Kenway",
            "preco":42.90,
            "restauranteId":%d
        }
        """.formatted(restaurante.getId());

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "haytham", roles = {"ADMIN"})
    void naoDeveCriarProdutoComNomeEmBranco() throws Exception {

        String json = """
        {
            "nome":"",
            "categoria":"Prato Principal",
            "descricao":"Receita secreta da Ordem dos Templarios",
            "preco":39.90,
            "restauranteId":%d
        }
        """.formatted(restaurante.getId());

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "haytham", roles = {"ADMIN"})
    void naoDeveCriarProdutoComPrecoInvalido() throws Exception {

        String json = """
        {
            "nome":"Cha de Boston",
            "categoria":"Bebida",
            "descricao":"Bebida historica servida no porto",
            "preco":0,
            "restauranteId":%d
        }
        """.formatted(restaurante.getId());

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "haytham", roles = {"ADMIN"})
    void naoDeveCriarProdutoComRestauranteInexistente() throws Exception {

        String json = """
        {
            "nome":"Torta de Maca Davenport",
            "categoria":"Sobremesa",
            "descricao":"Sobremesa feita na Homestead",
            "preco":24.90,
            "restauranteId":99999
        }
        """;

        mockMvc.perform(post("/api/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "haytham", roles = {"ADMIN"})
    void deveAlterarDisponibilidadeDoProduto() throws Exception {

        Produto produto = produtoRepository.save(Produto.builder()
                .nome("Pao Colonial Connor")
                .categoria("Acompanhamento")
                .descricao("Pao servido na Homestead")
                .preco(BigDecimal.valueOf(15.90))
                .disponivel(true)
                .restaurante(restaurante)
                .build());

        mockMvc.perform(patch("/api/produtos/" + produto.getId() + "/disponibilidade")
                .param("disponivel", "false"))
                .andExpect(status().isNoContent());
    }
}
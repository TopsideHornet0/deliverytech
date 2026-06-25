package com.deliverytech.controller;

import com.deliverytech.model.Cliente;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.ClienteRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Cliente cliente;
    private Restaurante restaurante;
    private Produto produto;

    @BeforeEach
    void prepararDados() {

        cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Connor Kenway")
                        .email("connor.pedido." + System.nanoTime() + "@assassins.com")
                        .ativo(true)
                        .build()
        );

        restaurante = restauranteRepository.save(
                Restaurante.builder()
                        .nome("Taverna Achilles")
                        .categoria("Comida Colonial")
                        .telefone("11999990003")
                        .taxaEntrega(BigDecimal.valueOf(6.50))
                        .tempoEntregaMinutos(35)
                        .ativo(true)
                        .build()
        );

        produto = produtoRepository.save(
                Produto.builder()
                        .nome("Ensopado da Fronteira")
                        .categoria("Prato Principal")
                        .descricao("Ensopado servido na Homestead")
                        .preco(BigDecimal.valueOf(41.90))
                        .disponivel(true)
                        .restaurante(restaurante)
                        .build()
        );
    }

    @Test
    @WithMockUser(username = "connor", roles = {"CLIENTE"})
    void deveCriarPedidoComSucesso() throws Exception {

        String json = """
        {
            "clienteId": %d,
            "restauranteId": %d,
            "enderecoEntrega": {
                "rua": "Rua Davenport",
                "numero": "1776",
                "bairro": "Homestead",
                "cidade": "Boston",
                "estado": "SP",
                "cep": "15000-000"
            },
            "itens": [
                {
                    "produtoId": %d,
                    "quantidade": 2
                }
            ]
        }
        """.formatted(
                cliente.getId(),
                restaurante.getId(),
                produto.getId()
        );

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "connor", roles = {"CLIENTE"})
    void naoDeveCriarPedidoComClienteInexistente() throws Exception {

        String json = """
        {
            "clienteId": 99999,
            "restauranteId": %d,
            "enderecoEntrega": {
                "rua": "Rua Achilles",
                "numero": "1777",
                "bairro": "Fronteira",
                "cidade": "Nova York",
                "estado": "SP",
                "cep": "15000-001"
            },
            "itens": [
                {
                    "produtoId": %d,
                    "quantidade": 1
                }
            ]
        }
        """.formatted(
                restaurante.getId(),
                produto.getId()
        );

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/api/pedidos"));
    }

    @Test
    @WithMockUser(username = "connor", roles = {"CLIENTE"})
    void naoDeveCriarPedidoComQuantidadeInvalida() throws Exception {

        String json = """
        {
            "clienteId": %d,
            "restauranteId": %d,
            "enderecoEntrega": {
                "rua": "Rua Haytham",
                "numero": "1754",
                "bairro": "Porto",
                "cidade": "Boston",
                "estado": "SP",
                "cep": "15000-002"
            },
            "itens": [
                {
                    "produtoId": %d,
                    "quantidade": 0
                }
            ]
        }
        """.formatted(
                cliente.getId(),
                restaurante.getId(),
                produto.getId()
        );

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/pedidos"))
                .andExpect(jsonPath("$.details").exists());
    }
}
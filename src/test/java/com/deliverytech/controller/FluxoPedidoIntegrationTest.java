package com.deliverytech.controller;

import com.deliverytech.model.Cliente;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.ClienteRepository;
import com.deliverytech.repository.ProdutoRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FluxoPedidoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @WithMockUser(username = "connor", roles = {"CLIENTE"})
    void deveCriarPedidoCompletoComClienteRestauranteEProduto() throws Exception {

        Cliente cliente = clienteRepository.save(Cliente.builder()
                .nome("Connor Kenway")
                .email("connor.fluxo." + System.nanoTime() + "@assassins.com")
                .ativo(true)
                .build());

        Restaurante restaurante = restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Davenport")
                .categoria("Comida Colonial")
                .telefone("11999990010")
                .taxaEntrega(BigDecimal.valueOf(7.50))
                .tempoEntregaMinutos(45)
                .ativo(true)
                .build());

        Produto produto = produtoRepository.save(Produto.builder()
                .nome("Ensopado Colonial")
                .categoria("Prato Principal")
                .descricao("Ensopado servido na Homestead")
                .preco(BigDecimal.valueOf(42.90))
                .disponivel(true)
                .restaurante(restaurante)
                .build());

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
        """.formatted(cliente.getId(), restaurante.getId(), produto.getId());

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.clienteId").value(cliente.getId()))
                .andExpect(jsonPath("$.restauranteId").value(restaurante.getId()))
                .andExpect(jsonPath("$.status").value("CRIADO"))
                .andExpect(jsonPath("$.total").value(85.80))
                .andExpect(jsonPath("$.itens[0].produtoId").value(produto.getId()))
                .andExpect(jsonPath("$.itens[0].nomeProduto").value("Ensopado Colonial"))
                .andExpect(jsonPath("$.itens[0].quantidade").value(2))
                .andExpect(jsonPath("$.itens[0].precoUnitario").value(42.90));
    }

    @Test
    @WithMockUser(username = "connor", roles = {"CLIENTE"})
    void naoDeveCriarPedidoSemItens() throws Exception {

        Cliente cliente = clienteRepository.save(Cliente.builder()
                .nome("Haytham Kenway")
                .email("haytham.fluxo." + System.nanoTime() + "@templarios.com")
                .ativo(true)
                .build());

        Restaurante restaurante = restauranteRepository.save(Restaurante.builder()
                .nome("Taverna Templaria")
                .categoria("Comida Colonial")
                .telefone("11999990011")
                .taxaEntrega(BigDecimal.valueOf(8.50))
                .tempoEntregaMinutos(50)
                .ativo(true)
                .build());

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
                "cep": "15000-001"
            },
            "itens": []
        }
        """.formatted(cliente.getId(), restaurante.getId());

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro de validação"))
                .andExpect(jsonPath("$.message").value("Campos inválidos na requisição"))
                .andExpect(jsonPath("$.path").value("/api/pedidos"))
                .andExpect(jsonPath("$.details.itens").exists());
    }
}
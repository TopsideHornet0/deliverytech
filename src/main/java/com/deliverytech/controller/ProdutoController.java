package com.deliverytech.controller;

import com.deliverytech.dto.request.ProdutoRequest;
import com.deliverytech.dto.response.ProdutoResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.ProdutoService;
import com.deliverytech.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(
        name = "Produtos",
        description = "Operações relacionadas ao gerenciamento de produtos"
)
public class ProdutoController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;

    @Operation(
            summary = "Cadastrar produto",
            description = "Cria um novo produto vinculado a um restaurante."
    )
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest request) {

        Restaurante restaurante = restauranteService.buscarPorId(request.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", request.getRestauranteId()));

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .disponivel(true)
                .restaurante(restaurante)
                .build();

        Produto salvo = produtoService.cadastrar(produto);

        return ResponseEntity.ok(new ProdutoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getDescricao(),
                salvo.getPreco(),
                salvo.getDisponivel()
        ));
    }

    @Operation(
            summary = "Listar produtos por restaurante",
            description = "Retorna todos os produtos de um restaurante específico."
    )
    @GetMapping("/restaurante/{restauranteId}")
    public List<ProdutoResponse> listarPorRestaurante(@PathVariable Long restauranteId) {

        restauranteService.buscarPorId(restauranteId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", restauranteId));

        return produtoService.buscarPorRestaurante(restauranteId).stream()
                .map(p -> new ProdutoResponse(
                        p.getId(),
                        p.getNome(),
                        p.getCategoria(),
                        p.getDescricao(),
                        p.getPreco(),
                        p.getDisponivel()
                ))
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequest request
    ) {

        Produto atualizado = Produto.builder()
                .nome(request.getNome())
                .categoria(request.getCategoria())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .build();

        Produto salvo = produtoService.atualizar(id, atualizado);

        return ResponseEntity.ok(new ProdutoResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getDescricao(),
                salvo.getPreco(),
                salvo.getDisponivel()
        ));
    }

    @Operation(
            summary = "Alterar disponibilidade",
            description = "Ativa ou desativa a disponibilidade de um produto."
    )
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> alterarDisponibilidade(
            @PathVariable Long id,
            @RequestParam boolean disponivel
    ) {

        produtoService.alterarDisponibilidade(id, disponivel);

        return ResponseEntity.noContent().build();
    }
}
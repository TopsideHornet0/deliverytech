package com.deliverytech.controller;

import com.deliverytech.dto.request.RestauranteRequest;
import com.deliverytech.dto.response.RestauranteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Restaurante;
import com.deliverytech.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
@Tag(
        name = "Restaurantes",
        description = "Operações relacionadas ao gerenciamento de restaurantes"
)
public class RestauranteController {

    private final RestauranteService restauranteService;

    @Operation(
            summary = "Cadastrar restaurante",
            description = "Cria um novo restaurante na plataforma."
    )
    @PostMapping
    public ResponseEntity<RestauranteResponse> cadastrar(@Valid @RequestBody RestauranteRequest request) {
        Restaurante restaurante = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .ativo(true)
                .build();

        Restaurante salvo = restauranteService.cadastrar(restaurante);

        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getTelefone(),
                salvo.getTaxaEntrega(),
                salvo.getTempoEntregaMinutos(),
                salvo.getAtivo()
        ));
    }

    @Operation(
            summary = "Listar restaurantes",
            description = "Lista todos os restaurantes com paginação."
    )
    @GetMapping
    public Page<RestauranteResponse> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.fromString(sortDirection),
                sortField
        );

        Page<Restaurante> restaurantesPaginados = restauranteService.listarTodos(pageable);

        return restaurantesPaginados.map(r -> new RestauranteResponse(
                r.getId(),
                r.getNome(),
                r.getCategoria(),
                r.getTelefone(),
                r.getTaxaEntrega(),
                r.getTempoEntregaMinutos(),
                r.getAtivo()
        ));
    }

    @Operation(
            summary = "Buscar restaurante por ID",
            description = "Retorna os dados de um restaurante específico."
    )
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));

        return ResponseEntity.ok(new RestauranteResponse(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getCategoria(),
                restaurante.getTelefone(),
                restaurante.getTaxaEntrega(),
                restaurante.getTempoEntregaMinutos(),
                restaurante.getAtivo()
        ));
    }

    @Operation(
            summary = "Buscar restaurantes por categoria",
            description = "Retorna todos os restaurantes de uma categoria específica."
    )
    @GetMapping("/categoria/{categoria}")
    public List<RestauranteResponse> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria).stream()
                .map(r -> new RestauranteResponse(
                        r.getId(),
                        r.getNome(),
                        r.getCategoria(),
                        r.getTelefone(),
                        r.getTaxaEntrega(),
                        r.getTempoEntregaMinutos(),
                        r.getAtivo()
                ))
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Atualizar restaurante",
            description = "Atualiza os dados de um restaurante existente."
    )
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RestauranteRequest request
    ) {
        Restaurante atualizado = Restaurante.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .categoria(request.getCategoria())
                .taxaEntrega(request.getTaxaEntrega())
                .tempoEntregaMinutos(request.getTempoEntregaMinutos())
                .build();

        Restaurante salvo = restauranteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new RestauranteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCategoria(),
                salvo.getTelefone(),
                salvo.getTaxaEntrega(),
                salvo.getTempoEntregaMinutos(),
                salvo.getAtivo()
        ));
    }

    @Operation(
            summary = "Limpar cache dos restaurantes",
            description = "Remove todas as entradas do cache de restaurantes."
    )
    @CacheEvict(value = "restaurantes", allEntries = true)
    @GetMapping("/cache/limpar")
    public ResponseEntity<Void> limparCache() {
        return ResponseEntity.noContent().build();
    }
}
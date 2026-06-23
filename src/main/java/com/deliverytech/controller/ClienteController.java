package com.deliverytech.controller;

import com.deliverytech.dto.request.ClienteRequest;
import com.deliverytech.dto.response.ClienteResponse;
import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(
        name = "Clientes",
        description = "Operações relacionadas ao gerenciamento de clientes"
)
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @Operation(
            summary = "Cadastrar cliente",
            description = "Cria um novo cliente na plataforma."
    )
    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody ClienteRequest request) {
        logger.info("Cadastro de cliente iniciado: {}", request.getEmail());

        Cliente cliente = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .ativo(true)
                .build();

        Cliente salvo = clienteService.cadastrar(cliente);

        logger.debug("Cliente salvo com ID {}", salvo.getId());

        return ResponseEntity.ok(new ClienteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getAtivo()
        ));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Lista todos os clientes ativos com paginação."
    )
    @GetMapping
    public Page<ClienteResponse> listar(Pageable pageable) {
        logger.info("Contas ativas na Plataforma");

        Page<Cliente> clientesPaginados = clienteService.listarAtivos(pageable);

        return clientesPaginados.map(c -> new ClienteResponse(
                c.getId(),
                c.getNome(),
                c.getEmail(),
                c.getAtivo()
        ));
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna os dados de um cliente específico."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable Long id) {
        logger.info("Buscando cliente com ID: {}", id);

        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", id));

        return ResponseEntity.ok(new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getAtivo()
        ));
    }

    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente existente."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request
    ) {
        logger.info("Atualizando cliente ID: {}", id);

        Cliente atualizado = Cliente.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .build();

        Cliente salvo = clienteService.atualizar(id, atualizado);

        return ResponseEntity.ok(new ClienteResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getAtivo()
        ));
    }

    @Operation(
            summary = "Ativar ou desativar cliente",
            description = "Alterna o status ativo/inativo de um cliente."
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> ativarDesativar(@PathVariable Long id) {
        logger.info("Alterando status do cliente ID: {}", id);
        clienteService.ativarDesativar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Status da API",
            description = "Verifica se a API está online."
    )
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        logger.debug("Status endpoint acessado");
        int cpuCores = Runtime.getRuntime().availableProcessors();
        logger.info("CPU cores disponíveis: {}", cpuCores);
        return ResponseEntity.ok("API está online");
    }
}
package com.deliverytech.service.impl;

import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.repository.ClienteRepository;
import com.deliverytech.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @CacheEvict(value = {"clientes", "clientesAtivos"}, allEntries = true)
    public Cliente cadastrar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Cacheable(value = "clientes", key = "#id")
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não localizado", id));
    }

    @Override
    @Cacheable(
            value = "clientesAtivos",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    public Page<Cliente> listarAtivos(Pageable pageable) {
        return clienteRepository.findByAtivoTrue(pageable);
    }

    @Override
    @CacheEvict(value = {"clientes", "clientesAtivos"}, allEntries = true)
    public Cliente atualizar(Long id, Cliente atualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não localizado", id));

        cliente.setNome(atualizado.getNome());
        cliente.setEmail(atualizado.getEmail());

        return clienteRepository.save(cliente);
    }

    @Override
    @CacheEvict(value = {"clientes", "clientesAtivos"}, allEntries = true)
    public void ativarDesativar(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente não localizado", id));

        cliente.setAtivo(!cliente.getAtivo());

        clienteRepository.save(cliente);
    }
}
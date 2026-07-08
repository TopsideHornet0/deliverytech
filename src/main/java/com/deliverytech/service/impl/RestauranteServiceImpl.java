package com.deliverytech.service.impl;

import com.deliverytech.exception.EntityNotFoundException;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.RestauranteRepository;
import com.deliverytech.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Override
    @CacheEvict(value = {"restaurantes", "restaurantePorId"}, allEntries = true)
    public Restaurante cadastrar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    @Cacheable(value = "restaurantePorId", key = "#id")
    public Optional<Restaurante> buscarPorId(Long id) {
        return restauranteRepository.findById(id);
    }

    @Override
    @Cacheable(
            value = "restaurantes",
            key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort"
    )
    public Page<Restaurante> listarTodos(Pageable pageable) {
        simulateDelay();
        return restauranteRepository.findAll(pageable);
    }

    @Override
    public List<Restaurante> buscarPorCategoria(String categoria) {
        return restauranteRepository.findByCategoria(categoria);
    }

    @Override
    @CacheEvict(value = {"restaurantes", "restaurantePorId"}, allEntries = true)
    public Restaurante atualizar(Long id, Restaurante atualizado) {
        return restauranteRepository.findById(id)
                .map(r -> {
                    r.setNome(atualizado.getNome());
                    r.setTelefone(atualizado.getTelefone());
                    r.setCategoria(atualizado.getCategoria());
                    r.setTaxaEntrega(atualizado.getTaxaEntrega());
                    r.setTempoEntregaMinutos(atualizado.getTempoEntregaMinutos());
                    return restauranteRepository.save(r);
                })
                .orElseThrow(() -> new EntityNotFoundException("Restaurante", id));
    }

    private void simulateDelay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
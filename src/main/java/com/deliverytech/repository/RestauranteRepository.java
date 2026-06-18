package com.deliverytech.repository;

import com.deliverytech.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    
    // Retorna uma 'Page' em vez de uma 'List' e aceita 'Pageable' para paginação
    Page<Restaurante> findByAtivoTrue(Pageable pageable);
}

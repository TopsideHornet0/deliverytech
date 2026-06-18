package com.deliverytech.exception;

public class EntityNotFoundException extends BusinessException {

    /**
     * Construtor que formata uma mensagem padrão de "não encontrado".
     * @param entityName Nome da entidade (Cliente, Produto, Restaurante, etc.)
     * @param id ID procurado
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s com ID %d não encontrado.", entityName, id));
    }

    /**
     * Construtor para mensagens personalizadas.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
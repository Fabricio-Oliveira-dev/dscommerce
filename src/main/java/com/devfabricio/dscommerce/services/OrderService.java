package com.devfabricio.dscommerce.services;

import com.devfabricio.dscommerce.dto.OrderDTO;
import com.devfabricio.dscommerce.entities.Order;
import com.devfabricio.dscommerce.repositories.OrderRepository;
import com.devfabricio.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new OrderDTO(order);
    }
}

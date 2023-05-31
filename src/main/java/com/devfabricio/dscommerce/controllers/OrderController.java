package com.devfabricio.dscommerce.controllers;

import com.devfabricio.dscommerce.dto.OrderDTO;
import com.devfabricio.dscommerce.dto.ProductDTO;
import com.devfabricio.dscommerce.dto.ProductMinDTO;
import com.devfabricio.dscommerce.entities.Order;
import com.devfabricio.dscommerce.services.OrderService;
import com.devfabricio.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }
}

package com.devfabricio.dscommerce.repositories;

import com.devfabricio.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

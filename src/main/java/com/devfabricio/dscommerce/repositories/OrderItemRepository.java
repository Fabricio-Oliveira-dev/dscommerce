package com.devfabricio.dscommerce.repositories;

import com.devfabricio.dscommerce.entities.OrderItem;
import com.devfabricio.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}

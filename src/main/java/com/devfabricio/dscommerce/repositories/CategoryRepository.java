package com.devfabricio.dscommerce.repositories;

import com.devfabricio.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

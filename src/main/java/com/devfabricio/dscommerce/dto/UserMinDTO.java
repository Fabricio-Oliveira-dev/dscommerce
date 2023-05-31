package com.devfabricio.dscommerce.dto;

import com.devfabricio.dscommerce.entities.User;

public class UserMinDTO {

    private Long id;
    private String name;

    public UserMinDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public UserMinDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

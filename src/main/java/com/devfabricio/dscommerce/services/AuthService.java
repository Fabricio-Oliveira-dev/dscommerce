package com.devfabricio.dscommerce.services;

import com.devfabricio.dscommerce.entities.User;
import com.devfabricio.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public void validateSelfOrAdmin(Long userId) {
        User me = userService.authenticated();
        if (me.hasRole("ROLE_ADMIN")) {
            return;
        }
        if (!me.getId().equals(userId)) {
            throw new ForbiddenException("Access denied. Should be self or admin");
        }
    }
}

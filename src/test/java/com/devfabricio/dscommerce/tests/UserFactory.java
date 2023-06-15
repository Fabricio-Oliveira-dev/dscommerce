package com.devfabricio.dscommerce.tests;

import com.devfabricio.dscommerce.entities.Role;
import com.devfabricio.dscommerce.entities.User;

import java.time.LocalDate;

public class UserFactory {

    public static User createClientUser() {
        User user = new User(1L, "Rodrigo", "rodrigo@gmail.com", "31999999999", LocalDate.parse("2001-09-11"), "$2a$10$TjmN4DAWkWJzYyTitqrPQeQKsxSbzponc7lzXNwDoj4pOQhkY5NI2");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User adminClientUser() {
        User user = new User(2L, "Paola", "paola@gmail.com", "41999999999", LocalDate.parse("2002-09-11"), "$2a$10$TjmN4DAWkWJzYyTitqrPQeQKsxSbzponc7lzXNwDoj4pOQhkY5NI2");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomClientUser(Long id, String username) {
        User user = new User(id, "Rodrigo", username, "31999999999", LocalDate.parse("2001-09-11"), "$2a$10$TjmN4DAWkWJzYyTitqrPQeQKsxSbzponc7lzXNwDoj4pOQhkY5NI2");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createCustomAdminUser(Long id, String username) {
        User user = new User(id, "Paola", username, "41999999999", LocalDate.parse("2002-09-11"), "$2a$10$TjmN4DAWkWJzYyTitqrPQeQKsxSbzponc7lzXNwDoj4pOQhkY5NI2");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }
}

package com.devfabricio.dscommerce.services;

import com.devfabricio.dscommerce.entities.User;
import com.devfabricio.dscommerce.projections.UserDetailsProjection;
import com.devfabricio.dscommerce.repositories.UserRepository;
import com.devfabricio.dscommerce.tests.UserDetailsFactory;
import com.devfabricio.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private String existingEmail, nonExistingEmail;
    private User user;
    private List<UserDetailsProjection> userProjection = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        existingEmail = "rodrigo@gmail.com";
        nonExistingEmail = "paola@gmail.com";
        user = UserFactory.createCustomClientUser(1L, existingEmail);
        userProjection = UserDetailsFactory.createCustomAdminUser(existingEmail);

        Mockito.when(repository.searchUserAndRolesByEmail(existingEmail)).thenReturn(userProjection);
        Mockito.when(repository.searchUserAndRolesByEmail(nonExistingEmail)).thenReturn(new ArrayList<>());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        UserDetails result = service.loadUserByUsername(existingEmail);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingEmail);
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(nonExistingEmail);
        });
    }
}

package com.devfabricio.dscommerce.services;

import com.devfabricio.dscommerce.dto.UserDTO;
import com.devfabricio.dscommerce.entities.User;
import com.devfabricio.dscommerce.projections.UserDetailsProjection;
import com.devfabricio.dscommerce.repositories.UserRepository;
import com.devfabricio.dscommerce.tests.UserDetailsFactory;
import com.devfabricio.dscommerce.tests.UserFactory;
import com.devfabricio.dscommerce.util.CustomUserUtil;
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
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private CustomUserUtil userUtil;

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

        Mockito.when(repository.findByEmail(existingEmail)).thenReturn(Optional.of(user));
        Mockito.when(repository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());
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

    @Test
    public void authenticatedShouldReturnUserWhenUserExists() {
        Mockito.when(userUtil.getLoggedUsername()).thenReturn(existingEmail);
        User result = service.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingEmail);
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
        });
    }

    @Test
    public void getMeShouldReturnUserDTOWhenUserAuthenticated() {

        UserService spyUserService = Mockito.spy(service);
        Mockito.doReturn(user).when(spyUserService).authenticated();

        UserDTO result = spyUserService.getMe();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getEmail(), existingEmail);
    }

    @Test
    public void getMeShouldThrowUsernameNotFoundExceptionWhenUserNotAuthenticated() {

        UserService spyUserService = Mockito.spy(service);
        Mockito.doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
           UserDTO result = spyUserService.getMe();
        });
    }
}

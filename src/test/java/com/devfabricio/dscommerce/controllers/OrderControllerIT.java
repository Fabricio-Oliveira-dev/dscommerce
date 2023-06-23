package com.devfabricio.dscommerce.controllers;

import com.devfabricio.dscommerce.entities.*;
import com.devfabricio.dscommerce.tests.ProductFactory;
import com.devfabricio.dscommerce.tests.UserFactory;
import com.devfabricio.dscommerce.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;
    private Long existingOrderId, nonExistingOrderId;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        existingOrderId = 1L;
        nonExistingOrderId = 100L;

        user = UserFactory.createClientUser();
        order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, user, null);

        Product product = ProductFactory.createProduct();
        OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
        order.getItems().add(orderItem);
    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenAdminLoggedAndIdExists() throws Exception {

        ResultActions actions = mockMvc.perform(get("/orders/{id}", existingOrderId)
                .header("Authorization", "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.id").value(existingOrderId));
        actions.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
        actions.andExpect(jsonPath("$.status").value("PAID"));
        actions.andExpect(jsonPath("$.user").exists());
        actions.andExpect(jsonPath("$.user.name").value("Maria Brown"));
        actions.andExpect(jsonPath("$.payment").exists());
        actions.andExpect(jsonPath("$.items").exists());
        actions.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
        actions.andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void findByIdShouldReturnOrderDTOWhenClientLoggedAndIdExists() throws Exception {

        ResultActions actions = mockMvc.perform(get("/orders/{id}", existingOrderId)
                        .header("Authorization", "Bearer " + clientToken)
                        .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.id").value(existingOrderId));
        actions.andExpect(jsonPath("$.moment").value("2022-07-25T13:00:00Z"));
        actions.andExpect(jsonPath("$.status").value("PAID"));
        actions.andExpect(jsonPath("$.user").exists());
        actions.andExpect(jsonPath("$.user.name").value("Maria Brown"));
        actions.andExpect(jsonPath("$.payment").exists());
        actions.andExpect(jsonPath("$.items").exists());
        actions.andExpect(jsonPath("$.items[1].name").value("Macbook Pro"));
        actions.andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void findByIdShouldReturnForbiddenWhenClientLoggedAndIdExistsAndOrderDoesNotBelongsUser() throws Exception {

        Long otherOrderId = 2L;
        ResultActions actions = mockMvc.perform(get("/orders/{id}", otherOrderId)
                .header("Authorization", "Bearer " + clientToken)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenAdminLoggedAndIdDoesNotExists() throws Exception {

        ResultActions actions = mockMvc.perform(get("/orders/{id}", nonExistingOrderId)
                .header("Authorization", "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenClientLoggedAndIdDoesNotExists() throws Exception {

        ResultActions actions = mockMvc.perform(get("/orders/{id}", nonExistingOrderId)
                .header("Authorization", "Bearer " + clientToken)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnUnauthorizedWhenNoRoleLogged() throws Exception {

        ResultActions actions = mockMvc.perform(get("/orders/{id}", existingOrderId)
                .header("Authorization", "Bearer " + invalidToken)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isUnauthorized());
    }
}

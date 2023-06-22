package com.devfabricio.dscommerce.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String productName;

    @BeforeEach
    void setUp() throws Exception {

        productName = "Macbook Pro";
    }


    @Test
    public void findAllShouldReturnProductPageByNameWhenNameParameterIsGiven() throws Exception {

        ResultActions actions = mockMvc.perform(get("/products?name={productName}", productName)
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.content[0].id").value(3L));
        actions.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        actions.andExpect(jsonPath("$.content[0].price").value(1250.0));
        actions.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
    }

    @Test
    public void findAllShouldReturnAllProductPagedWhenNameParameterIsNotGiven() throws Exception {

        ResultActions actions = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.content[0].id").value(1L));
        actions.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));
        actions.andExpect(jsonPath("$.content[0].price").value(90.5));
        actions.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
    }
}

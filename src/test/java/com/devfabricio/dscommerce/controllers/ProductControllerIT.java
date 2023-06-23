package com.devfabricio.dscommerce.controllers;

import com.devfabricio.dscommerce.dto.ProductDTO;
import com.devfabricio.dscommerce.entities.Category;
import com.devfabricio.dscommerce.entities.Product;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;
    private String productName;
    private Long existingProductId, nonExistingProductId, dependentProductId;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() throws Exception {

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        productName = "Macbook Pro";

        clientToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);
        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        Category category = new Category(3L, null);
        product = new Product(null, "PC Gamer", "awesome and very fast", 9298.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg");
        product.getCategories().add(category);
        productDTO = new ProductDTO(product);

        existingProductId = 1L;
        nonExistingProductId = 1000L;
        dependentProductId = 3L;
    }

    @Test
    public void findAllShouldReturnProductDTOPageByNameWhenNameParameterIsGiven() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/products?name={productName}", productName)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(3L));
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[0].price").value(1250.0));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/3-big.jpg"));
    }

    @Test
    public void findAllShouldReturnAllProductDTOPagedWhenNameParameterIsNotGiven() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/products")
                                .accept(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").value(1L));
        result.andExpect(jsonPath("$.content[0].name").value("The Lord of the Rings"));
        result.andExpect(jsonPath("$.content[0].price").value(90.5));
        result.andExpect(jsonPath("$.content[0].imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg"));
    }

    @Test
    public void insertShouldReturnProductDTO201WhenAdminLoggedAndValidData() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                                .header("Authorization", "Bearer " + adminToken)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print());

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").value("PC Gamer"));
        result.andExpect(jsonPath("$.description").value("awesome and very fast"));
        result.andExpect(jsonPath("$.price").value(9298.0));
        result.andExpect(jsonPath("$.imgUrl").value("https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/4-big.jpg"));
        result.andExpect(jsonPath("$.categories[0].id").value(3L));
    }

    @Test
    public void insertShouldReturnUnproccessableEntityWhenAdminLoggedAndNameFieldHasInvalidData() throws Exception {

        product.setName("ab");
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                                .header("Authorization", "Bearer " + adminToken)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnproccessableEntityWhenAdminLoggedAndDescriptionFieldHasInvalidData() throws Exception {

        product.setDescription("ab");
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnproccessableEntityWhenAdminLoggedAndPriceFieldHasNegativeData() throws Exception {

        product.setPrice(-100.0);
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnproccessableEntityWhenAdminLoggedAndPriceFieldIsZero() throws Exception {

        product.setPrice(0.0);
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnUnproccessableEntityWhenAdminLoggedAndProductHasNoCategory() throws Exception {

        product.getCategories().clear();
        productDTO = new ProductDTO(product);
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                                .header("Authorization", "Bearer " + clientToken)
                                .content(jsonBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldReturnUnauthorizedWhenNoRoleLogged() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
                mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + invalidToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteShouldReturnNoContentWhenAdminLoggedAndExistingProductIdIsGiven() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + adminToken));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenAdminLoggedAndNonExistingProductIdIsGiven() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/products/{id}", nonExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteShouldReturnBadRequestWhenAdminLoggedAndDependentProductIdIsGiven() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/products/{id}", dependentProductId)
                        .header("Authorization", "Bearer " + adminToken));

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnForbiddenWhenClientLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + clientToken));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldReturnUnauthorizedWhenNoRoleLogged() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/products/{id}", existingProductId)
                        .header("Authorization", "Bearer " + invalidToken));

        result.andExpect(status().isUnauthorized());
    }
}

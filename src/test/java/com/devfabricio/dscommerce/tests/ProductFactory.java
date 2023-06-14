package com.devfabricio.dscommerce.tests;

import com.devfabricio.dscommerce.entities.Category;
import com.devfabricio.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Category category = CategoryFactory.createCategory();
        Product product = new Product(1L, "Console PS5", "random text", 3999.0, "https://google.com");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }
}

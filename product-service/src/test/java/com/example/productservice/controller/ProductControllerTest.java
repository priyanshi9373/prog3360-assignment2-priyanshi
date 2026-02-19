package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.FeatureFlagService;
import com.example.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService productService;
    private FeatureFlagService featureFlagService;
    private ProductController controller;

    @BeforeEach
    void setupTests() {
        productService = mock(ProductService.class);
        featureFlagService = mock(FeatureFlagService.class);
        controller = new ProductController(productService, featureFlagService);
    }

    @Test
    void discountwhenpremiumPricing_flagOn() {
        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setPrice(120.0);
        laptop.setQuantity(5);

        when(productService.getAll()).thenReturn(List.of(laptop));
        when(featureFlagService.premiumPricingIsOn()).thenReturn(true);

        List<Product> response = controller.getPremiumProducts();

        double expectedPrice = 108.0;
        assertEquals(expectedPrice, response.get(0).getPrice());
    }

    @Test
    void samepricewhenpremiumPricing_flagOff() {
        Product phone = new Product();
        phone.setName("Phone");
        phone.setPrice(200.0);
        phone.setQuantity(2);

        when(productService.getAll()).thenReturn(List.of(phone));
        when(featureFlagService.premiumPricingIsOn()).thenReturn(false);

        List<Product> response = controller.getPremiumProducts();

        double expectedPrice = 200.0;
        assertEquals(expectedPrice, response.get(0).getPrice());
    }
}

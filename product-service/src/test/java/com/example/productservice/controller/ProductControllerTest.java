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
    void setUp() {
        productService = mock(ProductService.class);
        featureFlagService = mock(FeatureFlagService.class);
        controller = new ProductController(productService, featureFlagService);
    }

    @Test
    void premiumPricing_appliesDiscount_whenFlagOn() {
        Product p = new Product();
        p.setName("Test Product");
        p.setPrice(100.0);
        p.setQuantity(10);

        when(productService.getAll()).thenReturn(List.of(p));
        when(featureFlagService.isPremiumPricingEnabled()).thenReturn(true);

        List<Product> result = controller.getPremiumProducts();

        assertEquals(90.0, result.get(0).getPrice());
    }

    @Test
    void premiumPricing_noDiscount_whenFlagOff() {
        Product p = new Product();
        p.setName("Test Product");
        p.setPrice(100.0);
        p.setQuantity(10);

        when(productService.getAll()).thenReturn(List.of(p));
        when(featureFlagService.isPremiumPricingEnabled()).thenReturn(false);

        List<Product> result = controller.getPremiumProducts();

        assertEquals(100.0, result.get(0).getPrice());
    }
}

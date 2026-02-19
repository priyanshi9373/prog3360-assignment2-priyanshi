package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import com.example.productservice.service.FeatureFlagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final FeatureFlagService featureFlagService;

    public ProductController(ProductService service, FeatureFlagService featureFlagService) {
        this.service = service;
        this.featureFlagService = featureFlagService;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    @GetMapping("/premium")
    public List<Product> getPremiumProducts() {
        List<Product> products = service.getAll();

        if (!featureFlagService.premiumPricingIsOn()) {
            return products;
        }

        return products.stream()
                .map(p -> {
                    Product discounted = new Product();
                    discounted.setName(p.getName());
                    discounted.setQuantity(p.getQuantity());
                    discounted.setPrice(round2(p.getPrice() * 0.9));
                    return discounted;
                })
                .toList();
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

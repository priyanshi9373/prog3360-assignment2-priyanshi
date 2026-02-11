package com.example.orderservice.controller;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;
import com.example.orderservice.service.FeatureFlagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final FeatureFlagService featureFlagService;

    private final OrderService service;

    public OrderController(FeatureFlagService featureFlagService, OrderService service) {
        this.featureFlagService = featureFlagService;
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return service.createOrder(order);
    }

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return service.getById(id);
    }
}

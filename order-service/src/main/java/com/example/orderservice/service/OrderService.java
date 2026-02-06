package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public Order createOrder(Order order) {

        ProductResponse product = productClient.getProductById(order.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Insufficient product quantity");
        }

        double totalPrice = product.getPrice() * order.getQuantity();

        order.setTotalPrice(totalPrice);
        order.setStatus("CREATED");

        return repository.save(order);
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        Optional<Order> order = repository.findById(id);
        return order.orElse(null);
    }
}

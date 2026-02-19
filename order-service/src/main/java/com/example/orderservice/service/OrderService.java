package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final FeatureFlagService featureFlagService;

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient, FeatureFlagService featureFlagService)
    {
        this.repository = repository;
        this.productClient = productClient;
        this.featureFlagService = featureFlagService;
    }


    public Order createOrder(Order order) {

        ProductResponse product = productClient.getProductById(order.getProductId());

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Not enough product quantity");
        }

        double totalPrice = product.getPrice() * order.getQuantity();

        if (featureFlagService.isBulkOrderDiscountOn()
                && order.getQuantity() > 5) {

            totalPrice = totalPrice * 0.85; // 15% discount
        }

        order.setTotalPrice(totalPrice);
        order.setStatus("CREATED");

        Order created = repository.save(order);

        if (featureFlagService.isOrderNotificationsOn()) {
            log.info("ORDER NOTIFICATION: orderId={}, productId={}, productName={}, qty={}, totalPrice={}",
                    created.getId(),
                    product.getId(),
                    product.getName(),
                    order.getQuantity(),
                    totalPrice
            );
        }

        return created;
    }


    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        Optional<Order> order = repository.findById(id);
        return order.orElse(null);
    }
}

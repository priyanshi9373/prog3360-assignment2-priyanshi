package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderRepository repository;
    private ProductClient productClient;
    private FeatureFlagService featureFlagService;
    private OrderService service;

    @BeforeEach
    void setupTests() {
        repository = mock(OrderRepository.class);
        productClient = mock(ProductClient.class);
        featureFlagService = mock(FeatureFlagService.class);

        service = new OrderService(repository, productClient, featureFlagService);
    }

    @Test
    void discountwhenbulkFlag_flagOn() {

        ProductResponse laptop = new ProductResponse();
        laptop.setId(1L);
        laptop.setName("Laptop");
        laptop.setPrice(100.0);
        laptop.setQuantity(20);

        when(productClient.getProductById(1L)).thenReturn(laptop);
        when(featureFlagService.isBulkOrderDiscountOn()).thenReturn(true);
        when(featureFlagService.isOrderNotificationsOn()).thenReturn(false);
        Order savedOrder = new Order();
        savedOrder.setTotalPrice(510.0);
        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(6);

        Order response = service.createOrder(order);

        double expectedTotal = 510.0;
        assertEquals(expectedTotal, response.getTotalPrice());
    }

    @Test
    void nobulkdiscountwhen_FlagOff() {

        ProductResponse product = new ProductResponse();
        product.setId(1L);
        product.setName("Phone");
        product.setPrice(100.0);
        product.setQuantity(20);

        when(productClient.getProductById(1L)).thenReturn(product);
        when(featureFlagService.isBulkOrderDiscountOn()).thenReturn(false);
        when(featureFlagService.isOrderNotificationsOn()).thenReturn(false);
        Order savedOrder = new Order();
        savedOrder.setTotalPrice(600.0);
        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(6);

        Order response = service.createOrder(order);

        double expectedTotal = 600.0;
        assertEquals(expectedTotal, response.getTotalPrice());
    }
}

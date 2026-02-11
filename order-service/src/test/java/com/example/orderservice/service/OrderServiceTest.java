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
    void setUp() {
        repository = mock(OrderRepository.class);
        productClient = mock(ProductClient.class);
        featureFlagService = mock(FeatureFlagService.class);

        service = new OrderService(repository, productClient, featureFlagService);
    }

    @Test
    void bulkDiscount_applied_whenFlagOn_andQuantityGreaterThan5() {

        ProductResponse product = new ProductResponse();
        product.setId(1L);
        product.setName("Test");
        product.setPrice(100.0);
        product.setQuantity(20);

        when(productClient.getProductById(1L)).thenReturn(product);
        when(featureFlagService.isBulkOrderDiscountEnabled()).thenReturn(true);
        when(featureFlagService.isOrderNotificationsEnabled()).thenReturn(false);
        when(repository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(6);

        Order result = service.createOrder(order);

        assertEquals(510.0, result.getTotalPrice()); // 6 * 100 = 600 → 15% off = 510
    }

    @Test
    void bulkDiscount_notApplied_whenFlagOff() {

        ProductResponse product = new ProductResponse();
        product.setId(1L);
        product.setName("Test");
        product.setPrice(100.0);
        product.setQuantity(20);

        when(productClient.getProductById(1L)).thenReturn(product);
        when(featureFlagService.isBulkOrderDiscountEnabled()).thenReturn(false);
        when(featureFlagService.isOrderNotificationsEnabled()).thenReturn(false);
        when(repository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order order = new Order();
        order.setProductId(1L);
        order.setQuantity(6);

        Order result = service.createOrder(order);

        assertEquals(600.0, result.getTotalPrice()); // no discount
    }
}

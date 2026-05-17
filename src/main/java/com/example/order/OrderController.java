package com.example.ecommerceengine.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping
    public List<PurchaseOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public PurchaseOrder createOrder(@RequestBody Map<String, Integer> body) {
        Long walletId = body.get("walletId").longValue();
        Long productId = body.get("productId").longValue();
        Integer quantity = body.get("quantity");

        return orderService.createOrderWithRetry(walletId, productId, quantity);
    }
}
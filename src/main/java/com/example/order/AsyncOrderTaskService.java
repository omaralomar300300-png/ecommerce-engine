package com.example.ecommerceengine.order;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncOrderTaskService {

    @Async("backgroundTaskExecutor")
    public void generateInvoiceAsync(Long orderId) {
        try {
            Thread.sleep(2000);
            System.out.println("Invoice generated asynchronously for order id: " + orderId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
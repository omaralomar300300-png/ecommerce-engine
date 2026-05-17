package com.example.ecommerceengine.report;

import com.example.ecommerceengine.order.PurchaseOrder;
import com.example.ecommerceengine.order.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DailySalesReportController {

    private final OrderRepository orderRepository;

    public DailySalesReportController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/api/reports/daily-sales")
    public Map<String, Object> getDailySalesReport() {
        List<PurchaseOrder> orders = orderRepository.findAll();

        int chunkSize = 2;
        int totalOrders = 0;
        double totalRevenue = 0;

        for (int i = 0; i < orders.size(); i += chunkSize) {
            List<PurchaseOrder> chunk = orders.subList(
                    i,
                    Math.min(i + chunkSize, orders.size())
            );

            for (PurchaseOrder order : chunk) {
                totalOrders++;
                totalRevenue += order.getTotalPrice();
            }

            System.out.println("Processed chunk from index " + i + " with size " + chunk.size());
        }

        Map<String, Object> report = new HashMap<>();
        report.put("totalOrders", totalOrders);
        report.put("totalRevenue", totalRevenue);

        return report;
    }
}
package com.example.ecommerceengine.product;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // عرض كل المنتجات
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // عرض منتج واحد حسب id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // إضافة منتج جديد
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // تعديل كمية المخزون لمنتج معين
    @PutMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Integer newStockQuantity = body.get("stockQuantity");

        product.setStockQuantity(newStockQuantity);

        return productRepository.save(product);
    }
}
package com.example.ecommerceengine.order;

import com.example.ecommerceengine.invoicequeue.InvoiceTask;
import com.example.ecommerceengine.invoicequeue.InvoiceTaskRepository;
import com.example.ecommerceengine.product.Product;
import com.example.ecommerceengine.product.ProductRepository;
import com.example.ecommerceengine.wallet.Wallet;
import com.example.ecommerceengine.wallet.WalletRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;
    private final InvoiceTaskRepository invoiceTaskRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            WalletRepository walletRepository,
            InvoiceTaskRepository invoiceTaskRepository
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.walletRepository = walletRepository;
        this.invoiceTaskRepository = invoiceTaskRepository;
    }

    // حدود المعاملة:
    // خصم الرصيد، إنقاص المخزون، وإنشاء الطلب يجب أن تتم كعملية واحدة.
    // إذا فشلت أي خطوة، يتم التراجع عن العملية كاملة للحفاظ على سلامة البيانات.
    @Transactional
    public PurchaseOrder createOrder(Long walletId, Long productId, Integer quantity) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        double totalPrice = product.getPrice() * quantity;

        if (wallet.getBalance() < totalPrice) {
            throw new RuntimeException("Insufficient balance");
        }

        // هذا الجزء حساس لأنه يعدل بيانات مشتركة:
        // رصيد المستخدم وكمية المنتج في المخزون.
        // لذلك يتم تنفيذه داخل Transaction ومع استخدام Version لمنع التضارب.
        wallet.setBalance(wallet.getBalance() - totalPrice);
        product.setStockQuantity(product.getStockQuantity() - quantity);

        walletRepository.save(wallet);
        productRepository.save(product);

        PurchaseOrder order = new PurchaseOrder(
                product.getId(),
                product.getName(),
                quantity,
                totalPrice
        );

        PurchaseOrder savedOrder = orderRepository.save(order);

        // Asynchronous Queue:
        // بدل تنفيذ الفاتورة مباشرة داخل الطلب، يتم تسجيل مهمة في جدول invoice_task.
        // يقوم Worker في الخلفية بقراءة المهام ذات الحالة PENDING ومعالجتها لاحقًا.
        InvoiceTask invoiceTask = new InvoiceTask(savedOrder.getId());
        invoiceTaskRepository.save(invoiceTask);

        return savedOrder;
    }

    public PurchaseOrder createOrderWithRetry(Long walletId, Long productId, Integer quantity) {
        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                return createOrder(walletId, productId, quantity);
            } catch (ObjectOptimisticLockingFailureException ex) {
                if (attempt == maxRetries) {
                    throw new RuntimeException("Concurrent update conflict. Please retry later.");
                }
            }
        }

        throw new RuntimeException("Order failed");
    }
}
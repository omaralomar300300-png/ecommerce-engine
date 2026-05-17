package com.example.ecommerceengine.invoicequeue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class InvoiceQueueWorker {

    private final InvoiceTaskRepository invoiceTaskRepository;

    public InvoiceQueueWorker(InvoiceTaskRepository invoiceTaskRepository) {
        this.invoiceTaskRepository = invoiceTaskRepository;
    }

    // هذا worker يعمل في الخلفية كل 5 ثواني.
    // يقرأ المهام التي حالتها PENDING من جدول invoice_task
    // ثم يعالجها ويغير حالتها إلى DONE.
    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processPendingInvoiceTasks() {
        List<InvoiceTask> pendingTasks =
                invoiceTaskRepository.findTop5ByStatusOrderByCreatedAtAsc(TaskStatus.PENDING);

        for (InvoiceTask task : pendingTasks) {
            try {
                task.setStatus(TaskStatus.PROCESSING);
                task.setAttempts(task.getAttempts() + 1);

                System.out.println("Processing invoice task for order id: " + task.getOrderId());

                // محاكاة إصدار فاتورة
                Thread.sleep(2000);

                task.setStatus(TaskStatus.DONE);
                task.setProcessedAt(LocalDateTime.now());
                task.setErrorMessage(null);

                System.out.println("Invoice task completed for order id: " + task.getOrderId());

            } catch (Exception ex) {
                task.setStatus(TaskStatus.FAILED);
                task.setErrorMessage(ex.getMessage());

                System.out.println("Invoice task failed for order id: " + task.getOrderId());
            }

            invoiceTaskRepository.save(task);
        }
    }
}
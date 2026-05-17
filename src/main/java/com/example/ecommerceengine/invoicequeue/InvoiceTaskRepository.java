package com.example.ecommerceengine.invoicequeue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceTaskRepository extends JpaRepository<InvoiceTask, Long> {

    List<InvoiceTask> findTop5ByStatusOrderByCreatedAtAsc(TaskStatus status);
}
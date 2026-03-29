package com.selfhealing.ordersystem.service;

import com.selfhealing.ordersystem.dto.CreateOrderRequest;
import com.selfhealing.ordersystem.model.*;
import com.selfhealing.ordersystem.model.IdempotencyRecord;
import com.selfhealing.ordersystem.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final IdempotencyRepository idempotencyRepository;

    public OrderService(OrderRepository orderRepository,
                        IdempotencyRepository idempotencyRepository) {
        this.orderRepository = orderRepository;
        this.idempotencyRepository = idempotencyRepository;
    }

    public Order createOrder(CreateOrderRequest request) {

        // Step 1: Check existing record
        Optional<IdempotencyRecord> existing =
                idempotencyRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (existing.isPresent()) {
            IdempotencyRecord record = existing.get();

            // Case 1: already completed
            if ("SUCCESS".equals(record.getStatus()) && record.getOrderId() != null) {
                return orderRepository.findById(record.getOrderId()).orElseThrow();
            }

            //  Case 2: still processing
            if ("PROCESSING".equals(record.getStatus())) {
                throw new RuntimeException("Request is still processing");
            }
        }

        // Step 2: Save idempotency FIRST (important)
        IdempotencyRecord record = new IdempotencyRecord();
        record.setIdempotencyKey(request.getIdempotencyKey());
        record.setStatus("PROCESSING");
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());

        idempotencyRepository.save(record);

        // Step 3: Create order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(request.getTotalAmount());
        order.setIdempotencyKey(request.getIdempotencyKey());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        // Step 4: Update idempotency
        record.setStatus("SUCCESS");
        record.setOrderId(order.getId());
        record.setUpdatedAt(LocalDateTime.now());

        idempotencyRepository.save(record);

        return order;
    }
}
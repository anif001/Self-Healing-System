package com.selfhealing.ordersystem.repository;

import com.selfhealing.ordersystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository gives us save(), findById(), findAll() for free
}
package com.selfhealing.ordersystem.repository;
import com.selfhealing.ordersystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {

}

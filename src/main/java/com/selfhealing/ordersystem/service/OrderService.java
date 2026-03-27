package com.selfhealing.ordersystem.service;

import com.selfhealing.ordersystem.model.OrderStatus;
import com.selfhealing.ordersystem.model.Order;
import com.selfhealing.ordersystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
     Order order = new Order();


    public Order createOrder(Double amount,String currency)
    {
        order.setAmount(amount);
        order.setCurrency(currency);
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);

    }

}

package com.selfhealing.ordersystem.controller;
import com.selfhealing.ordersystem.model.Order;
import com.selfhealing.ordersystem.service.OrderService;
import org.springframework.web.bind.annotation.*;
import com.selfhealing.ordersystem.model.CreatedOrderRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
     // create order api
@PostMapping
    public  Order createOrder(@RequestBody CreatedOrderRequest request){
        return orderService.createOrder(

            request.getAmount(),
            request.getCurrency()
            );

    }
}

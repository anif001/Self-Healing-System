package com.selfhealing.ordersystem.worker;

import org.springframework.stereotype.Component;

@Component
public class OrderWorker {

    // Placeholder — in future lessons this will:
    // 1. Pick up CREATED orders
    // 2. Process them (check inventory, charge payment)
    // 3. Update status to SUCCESS or FAILED

    public void process() {
        System.out.println("OrderWorker: not yet implemented");
    }
}
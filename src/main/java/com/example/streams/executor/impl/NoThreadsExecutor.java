package com.example.streams.executor.impl;



import com.example.streams.executor.*;
import com.example.streams.model.*;
import com.example.streams.service.*;


import java.util.*;

public class NoThreadsExecutor implements CustomExecutor {

    OrderService orderService;
    CustomerService customerService;
    ProductService productService;

    public NoThreadsExecutor(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void task1(long milliseconds) {
        this.sleep(1000L);
        this.sleep(1000L);
    }

    @Override
    public Order task2(Long customerId, Long productId) {
        Customer customer = customerService.findById(customerId);
        Product product = productService.findById(productId);

        if(Objects.nonNull(customer) && Objects.nonNull(product))
            return orderService.save(customerId, productId);

        throw new RuntimeException("Failed to create order");
    }

    @Override
    public Dashboard task3(Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Order> orders;
        Set<Product> products;
        try {
            orders = orderService.findAllByCustomerId(customerId);
        } catch (Exception e) {
            orders = new ArrayList<>();
        }
        try {
            products = productService.findAll();
        } catch (Exception e) {
            products = new HashSet<>();
        }

        return new Dashboard(customer, orders, products);
    }


}

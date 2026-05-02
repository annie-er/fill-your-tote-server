package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.dto.OrderDTO;
import com.fillyourtote.fillyourtoteserver.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderDTO> getOrders() {
        return orderService.getOrdersForCurrentUser();
    }
}

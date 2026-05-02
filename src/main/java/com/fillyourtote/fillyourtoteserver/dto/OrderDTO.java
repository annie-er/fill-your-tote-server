package com.fillyourtote.fillyourtoteserver.dto;

import com.fillyourtote.fillyourtoteserver.entities.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime datePurchased;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;

    public OrderDTO(Order order, List<OrderItemDTO> items) {
        this.id = order.getId();
        this.datePurchased = order.getCreatedAt();
        this.totalAmount = order.getTotalAmount();
        this.items = items;
    }

    // getters; no setters
    public Long getId() { return id; }
    public LocalDateTime getDatePurchased() { return datePurchased; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<OrderItemDTO> getItems() { return items; }
}

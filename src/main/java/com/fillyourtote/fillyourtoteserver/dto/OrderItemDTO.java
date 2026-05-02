package com.fillyourtote.fillyourtoteserver.dto;

import com.fillyourtote.fillyourtoteserver.entities.OrderItem;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private String description;
    private String imageUrl;
    private int quantity;
    private BigDecimal priceAtPurchase;

    public OrderItemDTO(OrderItem item) {
        this.productName = item.getProductName();
        this.description = item.getDescription();
        this.imageUrl = item.getImageUrl();
        this.quantity = item.getQuantity();
        this.priceAtPurchase = item.getPriceAtPurchase();
    }

    // getters; no setters
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtPurchase() { return priceAtPurchase; }
}

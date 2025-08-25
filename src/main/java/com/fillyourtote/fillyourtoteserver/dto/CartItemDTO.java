package com.fillyourtote.fillyourtoteserver.dto;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import java.math.BigDecimal;

public class CartItemDTO {
    private Long id;
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String image;

    public CartItemDTO() {}

    public CartItemDTO(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProduct().getId().toString();
        this.name = cartItem.getProduct().getName();
        this.description = cartItem.getProduct().getDescription();
        this.price = cartItem.getProduct().getPrice();
        this.quantity = cartItem.getQuantity();
        this.image = cartItem.getProduct().getImageUrl();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
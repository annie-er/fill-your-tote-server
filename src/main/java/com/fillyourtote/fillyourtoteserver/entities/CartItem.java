package com.fillyourtote.fillyourtoteserver.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    // Null for guest carts; populated for authenticated users
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Populated for guest carts; null for authenticated users
    @Column(name = "guest_session_id")
    private String guestSessionId;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public CartItem() {}

    public CartItem(Product product, Integer quantity, User user) {
        this.product = product;
        this.quantity = quantity;
        this.user = user;
    }

    public CartItem(Product product, Integer quantity, String guestSessionId) {
        this.product = product;
        this.quantity = quantity;
        this.guestSessionId = guestSessionId;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public BigDecimal getSubtotal() {
        if (product != null && product.getPrice() != null && quantity != null) {
            return product.getPrice().multiply(BigDecimal.valueOf(quantity));
        }
        return BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getGuestSessionId() { return guestSessionId; }
    public void setGuestSessionId(String guestSessionId) { this.guestSessionId = guestSessionId; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "CartItem{id=" + id + ", product=" + product + ", quantity=" + quantity + '}';
    }
}
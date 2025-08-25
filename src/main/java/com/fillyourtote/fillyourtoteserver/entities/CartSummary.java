package com.fillyourtote.fillyourtoteserver.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CartSummary {
    private BigDecimal subtotal;
    private BigDecimal vatAmount;
    private BigDecimal total;

    private static final BigDecimal VAT_RATE = new BigDecimal("0.13"); // 13% tax for ontario res

    public CartSummary() {
        this.subtotal = BigDecimal.ZERO;
        this.vatAmount = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    public CartSummary(List<CartItem> cartItems) {
        calculateSummary(cartItems);
    }

    private void calculateSummary(List<CartItem> cartItems) {
        this.subtotal = cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        this.vatAmount = this.subtotal.multiply(VAT_RATE)
                .setScale(2, RoundingMode.HALF_UP);

        this.total = this.subtotal.add(this.vatAmount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
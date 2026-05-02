package com.fillyourtote.fillyourtoteserver.dto;

import jakarta.validation.constraints.NotNull;

public class AddToFavouritesRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    public AddToFavouritesRequest() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) {this.productId = productId; }
}

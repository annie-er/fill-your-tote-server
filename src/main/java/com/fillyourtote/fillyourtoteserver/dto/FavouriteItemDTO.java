package com.fillyourtote.fillyourtoteserver.dto;

import com.fillyourtote.fillyourtoteserver.entities.FavouriteItem;
import java.math.BigDecimal;

public class FavouriteItemDTO {
    private Long id;
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;

    public FavouriteItemDTO() {}

    public FavouriteItemDTO(FavouriteItem item) {
        this.id = item.getId();
        this.productId = item.getProduct().getId().toString();
        this.name = item.getProduct().getName();
        this.description = item.getProduct().getDescription();
        this.price = item.getProduct().getPrice();
        this.image = item.getProduct().getImageUrl();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}

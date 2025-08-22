package com.fillyourtote.fillyourtoteserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Drawing {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(length = 500)
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    public Drawing() {}

    public Drawing(String name, String slug, String description, String imageUrl) {
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Drawing drawing = (Drawing) o;
        return Objects.equals(id, drawing.id) &&
                Objects.equals(name, drawing.name) &&
                Objects.equals(slug, drawing.slug) &&
                Objects.equals(description, drawing.description) &&
                Objects.equals(imageUrl, drawing.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, slug, description, imageUrl);
    }

    @Override
    public String toString() {
        return "Drawing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
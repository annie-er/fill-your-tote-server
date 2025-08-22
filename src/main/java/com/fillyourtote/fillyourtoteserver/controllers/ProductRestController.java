package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    // contain read-only methods
    @GetMapping
    public List<Product> getProducts() {
        return service.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(service.findProductById(id));
    }
}
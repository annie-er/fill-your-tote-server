package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
//@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/{identifier}")
    public ResponseEntity<Product> getProduct(@PathVariable String identifier) {
        try {
            Long id = Long.parseLong(identifier);
            return ResponseEntity.of(service.findProductById(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.of(service.findProductBySlug(identifier));
        }
    }
}
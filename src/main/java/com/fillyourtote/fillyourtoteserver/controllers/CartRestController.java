package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.dto.CartItemDTO;
import com.fillyourtote.fillyourtoteserver.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartRestController {

    private final CartService service;

    public CartRestController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartItemDTO> getCartItems() {
        return service.getAllCartItemsDTO();
    }

    @GetMapping("/summary")
    public CartSummary getCartSummary() {
        return service.getCartSummary();
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addToCart(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        Integer quantity = request.containsKey("quantity") ?
                Integer.valueOf(request.get("quantity").toString()) : 1;

        return service.addToCart(productId, quantity)
                .map(item -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                            .path("/{id}")
                            .buildAndExpand(item.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(item);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long itemId,
                                                    @RequestBody Map<String, Object> request) {
        Integer quantity = Integer.valueOf(request.get("quantity").toString());

        if (quantity < 1) {
            return ResponseEntity.badRequest().build();
        }

        if (service.updateCartItemQuantity(itemId, quantity)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId) {
        if (service.removeCartItem(itemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart() {
        service.clearCart();
        return ResponseEntity.noContent().build();
    }
}
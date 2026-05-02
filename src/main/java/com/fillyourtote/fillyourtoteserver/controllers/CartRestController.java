package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.dto.AddToCartRequest;
import com.fillyourtote.fillyourtoteserver.dto.CartItemDTO;
import com.fillyourtote.fillyourtoteserver.dto.UpdateCartItemRequest;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.services.CartService;
import com.fillyourtote.fillyourtoteserver.utils.GuestSessionResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService service;
    private final GuestSessionResolver guestSessionResolver;

    public CartRestController(CartService service, GuestSessionResolver guestSessionResolver) {
        this.service = service;
        this.guestSessionResolver = guestSessionResolver;
    }

    @GetMapping
    public List<CartItemDTO> getCartItems(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = guestSessionResolver.resolve(request, response);
        return service.getAllCartItemsDTO(sessionId);
    }

    @GetMapping("/summary")
    public CartSummary getCartSummary(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = guestSessionResolver.resolve(request, response);
        return service.getCartSummary(sessionId);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody @Valid AddToCartRequest request,
                                              HttpServletRequest httpRequest,
                                              HttpServletResponse httpResponse) {
        String sessionId = guestSessionResolver.resolve(httpRequest, httpResponse);

        return service.addToCart(request.getProductId(), request.getQuantity(), sessionId)
                .map(item -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                            .path("/{id}").buildAndExpand(item.getId()).toUri();
                    return ResponseEntity.created(location).body(new CartItemDTO(item));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long itemId,
                                                    @RequestBody @Valid UpdateCartItemRequest request,
                                                    HttpServletRequest httpRequest,
                                                    HttpServletResponse httpResponse) {
        String sessionId = guestSessionResolver.resolve(httpRequest, httpResponse);

        return service.updateCartItemQuantity(itemId, request.getQuantity(), sessionId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId,
                                            HttpServletRequest httpRequest,
                                            HttpServletResponse httpResponse) {
        String sessionId = guestSessionResolver.resolve(httpRequest, httpResponse);
        return service.removeCartItem(itemId, sessionId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String sessionId = guestSessionResolver.resolve(httpRequest, httpResponse);
        service.clearCart(sessionId);
        return ResponseEntity.noContent().build();
    }
}
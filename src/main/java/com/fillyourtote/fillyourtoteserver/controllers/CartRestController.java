package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.dto.CartItemDTO;
import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.services.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private static final String GUEST_SESSION_COOKIE = "guestSessionId";
    private final CartService service;

    public CartRestController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartItemDTO> getCartItems(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = resolveGuestSession(request, response);
        return service.getAllCartItemsDTO(sessionId);
    }

    @GetMapping("/summary")
    public CartSummary getCartSummary(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = resolveGuestSession(request, response);
        return service.getCartSummary(sessionId);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addToCart(@RequestBody Map<String, Object> request,
                                              HttpServletRequest httpRequest,
                                              HttpServletResponse httpResponse) {
        String sessionId = resolveGuestSession(httpRequest, httpResponse);
        Long productId = Long.valueOf(request.get("productId").toString());
        Integer quantity = request.containsKey("quantity")
                ? Integer.valueOf(request.get("quantity").toString()) : 1;

        return service.addToCart(productId, quantity, sessionId)
                .map(item -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                            .path("/{id}").buildAndExpand(item.getId()).toUri();
                    return ResponseEntity.created(location).body(item);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long itemId,
                                                    @RequestBody Map<String, Object> request,
                                                    HttpServletRequest httpRequest,
                                                    HttpServletResponse httpResponse) {
        String sessionId = resolveGuestSession(httpRequest, httpResponse);
        Integer quantity = Integer.valueOf(request.get("quantity").toString());

        if (quantity < 1) return ResponseEntity.badRequest().build();

        return service.updateCartItemQuantity(itemId, quantity, sessionId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId,
                                            HttpServletRequest httpRequest,
                                            HttpServletResponse httpResponse) {
        String sessionId = resolveGuestSession(httpRequest, httpResponse);
        return service.removeCartItem(itemId, sessionId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String sessionId = resolveGuestSession(httpRequest, httpResponse);
        service.clearCart(sessionId);
        return ResponseEntity.noContent().build();
    }

    // Creates a guest session cookie if one doesn't already exist
    private String resolveGuestSession(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> GUEST_SESSION_COOKIE.equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseGet(() -> createGuestSessionCookie(response));
        }
        return createGuestSessionCookie(response);
    }

    private String createGuestSessionCookie(HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(GUEST_SESSION_COOKIE, sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(cookie);
        return sessionId;
    }
}
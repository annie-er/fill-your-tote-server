package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.security.SecurityContextHelper;
import com.fillyourtote.fillyourtoteserver.services.CartService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final String GUEST_SESSION_COOKIE = "guestSessionId";

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    private final CartService cartService;
    private final SecurityContextHelper securityContextHelper;

    public PaymentController(CartService cartService, SecurityContextHelper securityContextHelper) {
        this.cartService = cartService;
        this.securityContextHelper = securityContextHelper;
    }

    @PostMapping("/create-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(
            HttpServletRequest request,
            HttpServletResponse response) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        String sessionId = resolveGuestSession(request, response);

        CartSummary summary = cartService.getCartSummary(sessionId);

        BigDecimal total = summary.getTotal();

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cart is empty"));
        }

        long amountInCents = total
                .multiply(BigDecimal.valueOf(100))
                .longValueExact();

        String userId = securityContextHelper.getCurrentUser()
                .map(u -> u.getId().toString())
                .orElse(null);

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("cad")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .putMetadata("sessionId", sessionId)
                .putMetadata("userId", userId != null ? userId : "")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        return ResponseEntity.ok(Map.of("clientSecret", intent.getClientSecret()));
    }

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
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return sessionId;
    }
}
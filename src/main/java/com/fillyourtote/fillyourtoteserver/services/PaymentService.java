package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.dto.ErrorResponseDTO;
import com.fillyourtote.fillyourtoteserver.dto.PaymentIntentResponseDTO;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.security.SecurityContextHelper;
import com.fillyourtote.fillyourtoteserver.utils.GuestSessionResolver;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    private final CartService cartService;
    private final SecurityContextHelper securityContextHelper;
    private final GuestSessionResolver guestSessionResolver;

    public PaymentService(CartService cartService, SecurityContextHelper securityContextHelper,  GuestSessionResolver guestSessionResolver) {
        this.cartService = cartService;
        this.securityContextHelper = securityContextHelper;
        this.guestSessionResolver = guestSessionResolver;
    }

    public ResponseEntity<?> createIntent(HttpServletRequest request, HttpServletResponse response) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        String sessionId = guestSessionResolver.resolve(request, response);

        CartSummary summary = cartService.getCartSummary(sessionId);
        BigDecimal total = summary.getTotal();

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Cart is empty"));
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

        return ResponseEntity.ok(new PaymentIntentResponseDTO(intent.getClientSecret()));
    }
}
package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    private final OrderService orderService;

    public WebhookController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        logger.info("Webhook endpoint hit");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            logger.info("Event type: {}", event.getType());
        } catch (SignatureVerificationException e) {
            logger.error("Invalid Stripe signature: {}", e.getMessage());
            return ResponseEntity.status(400).body("Invalid signature");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            logger.info("Processing payment_intent.succeeded");

            // Replace orElseThrow() with raw JSON deserialization
            PaymentIntent intent = (PaymentIntent) event.getData().getObject();

            if (intent == null) {
                logger.error("Could not deserialize PaymentIntent from event");
                return ResponseEntity.status(400).body("Deserialization failed");
            }

            orderService.createOrderFromIntent(intent);
        }

        return ResponseEntity.ok("received");
    }
}
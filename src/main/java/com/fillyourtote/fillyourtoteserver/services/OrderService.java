package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.entities.*;
import com.fillyourtote.fillyourtoteserver.dao.OrderRepository;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public void createOrderFromIntent(PaymentIntent intent) {
        Stripe.apiKey = stripeSecretKey;

        String paymentIntentId = intent.getId();
        logger.info("Webhook received for PaymentIntent: {}", paymentIntentId);

        if (orderRepository.existsByStripePaymentIntentId(paymentIntentId)) {
            logger.info("Order already exists for intent {}, skipping", paymentIntentId);
            return;
        }

        String sessionId = intent.getMetadata().get("sessionId");
        logger.info("Session ID from metadata: {}", sessionId);

        Charge charge;
        try {
            String chargeId = intent.getLatestCharge();  // just the ID string
            logger.info("Charge ID from intent: {}", chargeId);
            charge = Charge.retrieve(chargeId);
            logger.info("Charge retrieved successfully");
        } catch (Exception e) {
            logger.error("Failed to retrieve charge: {}", e.getMessage());
            return;
        }

        com.stripe.model.Address stripeAddress = charge.getBillingDetails().getAddress();

        String userIdStr = intent.getMetadata().get("userId");

        List<CartItem> cartItems;
        if (userIdStr != null && !userIdStr.isBlank()) {
            cartItems = cartService.getCartItemsByUserId(Long.parseLong(userIdStr));
        } else {
            cartItems = cartService.getCartItemsBySessionId(sessionId);
        }
        logger.info("Cart items found: {}", cartItems.size());

        Order order = new Order();
        order.setSessionId(sessionId);
        order.setStripePaymentIntentId(paymentIntentId);
        order.setStatus(OrderStatus.PAID);
        order.setCustomerEmail(charge.getBillingDetails().getEmail());
        order.setCustomerName(charge.getBillingDetails().getName());
        order.setAddressLine1(stripeAddress.getLine1());
        order.setCity(stripeAddress.getCity());
        order.setPostalCode(stripeAddress.getPostalCode());
        order.setCountry(stripeAddress.getCountry());
        order.setTotalAmount(
                BigDecimal.valueOf(intent.getAmount()).divide(BigDecimal.valueOf(100))
        );

        // Save order first so it gets an ID
        order.setItems(null); // don't set items yet
        orderRepository.save(order);

        // Now save items with the persisted order reference
        List<OrderItem> items = cartItems.stream().map(ci -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(ci.getProduct().getId());
            item.setProductName(ci.getProduct().getName());
            item.setQuantity(ci.getQuantity());
            item.setPriceAtPurchase(ci.getProduct().getPrice());
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        orderRepository.save(order); // save again with items

        if (userIdStr != null && !userIdStr.isBlank()) {
            cartService.clearCartByUserId(Long.parseLong(userIdStr));
        } else {
            cartService.clearCartBySessionId(sessionId);
        }
    }
}

package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CartRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getCartItems() {
        webTestClient.get()
                .uri("/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CartItem.class)
                .consumeWith(System.out::println);
    }

    @Test
    void addToCartWithQuantity() {
        // First create a product
        Product product = new Product("Test Product", "Test Description", "test-image.jpg", BigDecimal.valueOf(19.99));

        Product savedProduct = webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        // Create add to cart request
        String requestBody = """
            {
                "productId": %d,
                "quantity": 2
            }
            """.formatted(savedProduct.getId());

        webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .consumeWith(System.out::println);
    }

    @Test
    void addToCartWithoutQuantity() {
        // First create a product
        Product product = new Product("Test Product", "Test Description", "test-image.jpg", BigDecimal.valueOf(19.99));

        Product savedProduct = webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        String requestBody = """
            {
                "productId": %d
            }
            """.formatted(savedProduct.getId());

        webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .consumeWith(System.out::println);
    }

    @Test
    void addToCartServiceReturnsEmpty() {
        String requestBody = """
            {
                "productId": 999999,
                "quantity": 2
            }
            """;

        webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateCartItemQuantitySuccess() {
        // First create a product and add to cart
        Product product = new Product("Test Product", "Test Description", "test-image.jpg", BigDecimal.valueOf(19.99));

        Product savedProduct = webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        String addRequestBody = """
            {
                "productId": %d,
                "quantity": 1
            }
            """.formatted(savedProduct.getId());

        CartItem cartItem = webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addRequestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CartItem.class)
                .returnResult()
                .getResponseBody();

        String updateRequestBody = """
            {
                "quantity": 3
            }
            """;

        webTestClient.put()
                .uri("/cart/items/{id}", cartItem.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequestBody)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateCartItemQuantityInvalidQuantity() {
        String requestBody = """
            {
                "quantity": 0
            }
            """;

        webTestClient.put()
                .uri("/cart/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateCartItemQuantityNotFound() {
        String requestBody = """
            {
                "quantity": 3
            }
            """;

        webTestClient.put()
                .uri("/cart/items/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void removeCartItemSuccess() {
        // First create a product and add to cart
        Product product = new Product("Test Product", "Test Description", "test-image.jpg", BigDecimal.valueOf(19.99));

        Product savedProduct = webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        String addRequestBody = """
            {
                "productId": %d,
                "quantity": 1
            }
            """.formatted(savedProduct.getId());

        CartItem cartItem = webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addRequestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CartItem.class)
                .returnResult()
                .getResponseBody();

        // when: delete the item
        webTestClient.delete()
                .uri("/cart/items/{id}", cartItem.getId())
                .exchange()
                .expectStatus().isNoContent();

        // then: verify item is removed (optional verification)
        webTestClient.get()
                .uri("/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CartItem.class)
                .consumeWith(System.out::println);
    }

    @Test
    void removeCartItemNotFound() {
        webTestClient.delete()
                .uri("/cart/items/999999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void clearCart() {
        // First create a product and add to cart
        Product product = new Product("Test Product", "Test Description", "test-image.jpg", BigDecimal.valueOf(19.99));

        Product savedProduct = webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody();

        String addRequestBody = """
            {
                "productId": %d,
                "quantity": 1
            }
            """.formatted(savedProduct.getId());

        webTestClient.post()
                .uri("/cart/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addRequestBody)
                .exchange()
                .expectStatus().isCreated();

        // when: clear the cart
        webTestClient.delete()
                .uri("/cart")
                .exchange()
                .expectStatus().isNoContent();

        // then: verify cart is empty
        webTestClient.get()
                .uri("/cart")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CartItem.class).hasSize(0);
    }
}
package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.dto.CartItemDTO;
import com.fillyourtote.fillyourtoteserver.dao.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CartItemDTO> getAllCartItemsDTO() {
        return cartItemRepository.findAll().stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CartSummary getCartSummary() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return new CartSummary(cartItems);
    }

    public Optional<CartItem> addToCart(Long productId, Integer quantity) {
        Optional<Product> productOpt = productService.findProductById(productId);
        if (productOpt.isPresent()) {
            List<CartItem> existingItems = cartItemRepository.findAll();
            for (CartItem item : existingItems) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return Optional.of(cartItemRepository.save(item));
                }
            }

            CartItem newItem = new CartItem(productOpt.get(), quantity);
            return Optional.of(cartItemRepository.save(newItem));
        }
        return Optional.empty();
    }

    public boolean updateCartItemQuantity(Long itemId, Integer quantity) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean removeCartItem(Long itemId) {
        if (cartItemRepository.existsById(itemId)) {
            cartItemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
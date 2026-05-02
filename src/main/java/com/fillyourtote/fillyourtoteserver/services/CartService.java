package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.dao.CartItemRepository;
import com.fillyourtote.fillyourtoteserver.dto.CartItemDTO;
import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.CartSummary;
import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.entities.User;
import com.fillyourtote.fillyourtoteserver.security.SecurityContextHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final SecurityContextHelper securityContextHelper;

    public CartService(CartItemRepository cartItemRepository,
                       ProductService productService,
                       SecurityContextHelper securityContextHelper) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.securityContextHelper = securityContextHelper;
    }

    @Transactional(readOnly = true)
    public List<CartItemDTO> getAllCartItemsDTO(String guestSessionId) {
        return getCartItems(guestSessionId).stream()
                .map(CartItemDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CartSummary getCartSummary(String guestSessionId) {
        return new CartSummary(getCartItems(guestSessionId));
    }

    public Optional<CartItem> addToCart(Long productId, Integer quantity, String guestSessionId) {
        Optional<Product> productOpt = productService.findProductById(productId);
        if (productOpt.isEmpty()) return Optional.empty();

        Optional<User> userOpt = securityContextHelper.getCurrentUser();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<CartItem> existing = cartItemRepository.findByUserAndProductId(user, productId);
            if (existing.isPresent()) {
                CartItem item = existing.get();
                item.setQuantity(item.getQuantity() + quantity);
                return Optional.of(cartItemRepository.save(item));
            }
            CartItem newItem = new CartItem(productOpt.get(), quantity, user);
            return Optional.of(cartItemRepository.save(newItem));
        } else {
            // Guest
            Optional<CartItem> existing = cartItemRepository.findByGuestSessionIdAndProductId(guestSessionId, productId);
            if (existing.isPresent()) {
                CartItem item = existing.get();
                item.setQuantity(item.getQuantity() + quantity);
                return Optional.of(cartItemRepository.save(item));
            }
            CartItem newItem = new CartItem(productOpt.get(), quantity, guestSessionId);
            return Optional.of(cartItemRepository.save(newItem));
        }
    }

    public boolean updateCartItemQuantity(Long itemId, Integer quantity, String guestSessionId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent() && isOwner(itemOpt.get(), guestSessionId)) {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean removeCartItem(Long itemId, String guestSessionId) {
        Optional<CartItem> itemOpt = cartItemRepository.findById(itemId);
        if (itemOpt.isPresent() && isOwner(itemOpt.get(), guestSessionId)) {
            cartItemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

    public void clearCart(String guestSessionId) {
        Optional<User> userOpt = securityContextHelper.getCurrentUser();
        if (userOpt.isPresent()) {
            cartItemRepository.deleteByUser(userOpt.get());
        } else {
            cartItemRepository.deleteByGuestSessionId(guestSessionId);
        }
    }

    private List<CartItem> getCartItems(String guestSessionId) {
        Optional<User> userOpt = securityContextHelper.getCurrentUser();
        return userOpt.isPresent()
                ? cartItemRepository.findByUser(userOpt.get())
                : cartItemRepository.findByGuestSessionId(guestSessionId);
    }

    private boolean isOwner(CartItem item, String guestSessionId) {
        Optional<User> userOpt = securityContextHelper.getCurrentUser();
        if (userOpt.isPresent()) {
            return item.getUser() != null && item.getUser().getId().equals(userOpt.get().getId());
        }
        return guestSessionId != null && guestSessionId.equals(item.getGuestSessionId());
    }

    public void mergeGuestCart(String guestSessionId, User user) {
        if (guestSessionId == null || guestSessionId.isBlank()) return;

        List<CartItem> guestItems = cartItemRepository.findByGuestSessionId(guestSessionId);

        for (CartItem guestItem : guestItems) {
            Optional<CartItem> existingUserItem = cartItemRepository
                    .findByUserAndProductId(user, guestItem.getProduct().getId());

            if (existingUserItem.isPresent()) {
                // Product already in user's cart — combine quantities
                CartItem userItem = existingUserItem.get();
                userItem.setQuantity(userItem.getQuantity() + guestItem.getQuantity());
                cartItemRepository.save(userItem);
                cartItemRepository.delete(guestItem);
            } else {
                // Product not in user's cart — transfer guest item to user
                guestItem.setUser(user);
                guestItem.setGuestSessionId(null);
                cartItemRepository.save(guestItem);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartItemsBySessionId(String guestSessionId) {
        return cartItemRepository.findByGuestSessionId(guestSessionId);
    }

    public void clearCartBySessionId(String guestSessionId) {
        cartItemRepository.deleteByGuestSessionId(guestSessionId);
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCartByUserId(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
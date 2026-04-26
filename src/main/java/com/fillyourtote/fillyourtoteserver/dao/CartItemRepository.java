package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import com.fillyourtote.fillyourtoteserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    List<CartItem> findByGuestSessionId(String guestSessionId);
    Optional<CartItem> findByUserAndProductId(User user, Long productId);
    Optional<CartItem> findByGuestSessionIdAndProductId(String guestSessionId, Long productId);
    List<CartItem> findByUserId(Long userId);
    void deleteByUser(User user);
    void deleteByGuestSessionId(String guestSessionId);
    void deleteByGuestSessionIdIsNotNullAndCreatedAtBefore(LocalDateTime cutoff);
    void deleteByUserId(Long userId);
}
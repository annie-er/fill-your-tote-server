package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
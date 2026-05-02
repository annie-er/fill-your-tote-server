package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.Order;
import com.fillyourtote.fillyourtoteserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByStripePaymentIntentId(String stripePaymentIntentId);
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
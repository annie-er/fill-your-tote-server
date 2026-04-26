package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.FavouriteItem;
import com.fillyourtote.fillyourtoteserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteItemRepository extends JpaRepository<FavouriteItem, Long> {
    List<FavouriteItem> findByUser(User user);
    Optional<FavouriteItem> findByUserAndProductId(User user, Long productId);
    void deleteByUser(User user);
}
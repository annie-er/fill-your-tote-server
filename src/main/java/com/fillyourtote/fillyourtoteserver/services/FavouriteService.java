package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.dao.FavouriteItemRepository;
import com.fillyourtote.fillyourtoteserver.dto.FavouriteItemDTO;
import com.fillyourtote.fillyourtoteserver.entities.FavouriteItem;
import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.entities.User;
import com.fillyourtote.fillyourtoteserver.security.SecurityContextHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavouriteService {

    private final FavouriteItemRepository favouriteItemRepository;
    private final ProductService productService;
    private final SecurityContextHelper securityContextHelper;

    public FavouriteService(FavouriteItemRepository favouriteItemRepository,
                            ProductService productService,
                            SecurityContextHelper securityContextHelper) {
        this.favouriteItemRepository = favouriteItemRepository;
        this.productService = productService;
        this.securityContextHelper = securityContextHelper;
    }

    @Transactional(readOnly = true)
    public List<FavouriteItemDTO> getAllFavouritesDTO() {
        User user = requireCurrentUser();
        return favouriteItemRepository.findByUser(user).stream()
                .map(FavouriteItemDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<FavouriteItem> addToFavourites(Long productId) {
        User user = requireCurrentUser();
        Optional<Product> productOpt = productService.findProductById(productId);
        if (productOpt.isEmpty()) return Optional.empty();

        if (favouriteItemRepository.findByUserAndProductId(user, productId).isPresent()) {
            return Optional.empty(); // already favourited
        }

        FavouriteItem newItem = new FavouriteItem(productOpt.get(), user);
        return Optional.of(favouriteItemRepository.save(newItem));
    }

    public boolean removeFromFavourites(Long itemId) {
        User user = requireCurrentUser();
        Optional<FavouriteItem> itemOpt = favouriteItemRepository.findById(itemId);
        if (itemOpt.isPresent() && itemOpt.get().getUser().getId().equals(user.getId())) {
            favouriteItemRepository.deleteById(itemId);
            return true;
        }
        return false;
    }

    public void clearFavourites() {
        User user = requireCurrentUser();
        favouriteItemRepository.deleteByUser(user);
    }

    private User requireCurrentUser() {
        return securityContextHelper.getCurrentUser()
                .orElseThrow(() -> new SecurityException("Authentication required"));
    }
}
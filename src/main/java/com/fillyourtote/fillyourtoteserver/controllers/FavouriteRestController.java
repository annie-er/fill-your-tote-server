package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.dto.AddToFavouritesRequest;
import com.fillyourtote.fillyourtoteserver.dto.FavouriteItemDTO;
import com.fillyourtote.fillyourtoteserver.entities.FavouriteItem;
import com.fillyourtote.fillyourtoteserver.services.FavouriteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favourites")
public class FavouriteRestController {

    private final FavouriteService service;

    public FavouriteRestController(FavouriteService service) {
        this.service = service;
    }

    @GetMapping
    public List<FavouriteItemDTO> getFavourites() {
        return service.getAllFavouritesDTO();
    }

    @PostMapping("/items")
    public ResponseEntity<FavouriteItemDTO> addToFavourites(@RequestBody @Valid AddToFavouritesRequest request) {

        return service.addToFavourites(request.getProductId())
                .map(item -> {
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                            .path("/{id}")
                            .buildAndExpand(item.getId())
                            .toUri();
                    return ResponseEntity.created(location).body(new FavouriteItemDTO(item));
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeFromFavourites(@PathVariable Long itemId) {
        if (service.removeFromFavourites(itemId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearFavourites() {
        service.clearFavourites();
        return ResponseEntity.noContent().build();
    }
}
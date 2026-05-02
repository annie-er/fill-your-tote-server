package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.Drawing;
import com.fillyourtote.fillyourtoteserver.services.DrawingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drawings")
public class DrawingRestController {

    private final DrawingService service;

    public DrawingRestController(DrawingService service) {
        this.service = service;
    }

    // also read-only methods
    @GetMapping
    public List<Drawing> getDrawings() {
        return service.findAllDrawings();
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Drawing> getDrawing(@PathVariable String identifier) {
        try {
            Long id = Long.parseLong(identifier);
            return ResponseEntity.of(service.findDrawingById(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.of(service.findDrawingBySlug(identifier));
        }
    }
}
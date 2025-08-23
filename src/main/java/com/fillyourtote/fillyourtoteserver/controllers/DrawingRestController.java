package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.Drawing;
import com.fillyourtote.fillyourtoteserver.services.DrawingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drawings")
@CrossOrigin(origins = "http://localhost:5173")
public class DrawingRestController {

    private final DrawingService service;

    public DrawingRestController(DrawingService service) {
        this.service = service;
    }

    // also read-only methods
    @GetMapping
    public List<Drawing> getDrawings() {
        System.out.println("=== CONTROLLER START ===");
        List<Drawing> drawings = service.findAllDrawings();

        System.out.println("=== CONTROLLER RECEIVED " + drawings.size() + " DRAWINGS ===");
        drawings.forEach(d ->
                System.out.println("Controller sees: ID=" + d.getId() + ", Name='" + d.getName() + "', Slug='" + d.getSlug() + "'")
        );
        System.out.println("=== CONTROLLER END ===");

        return drawings;
//        return service.findAllDrawings();
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

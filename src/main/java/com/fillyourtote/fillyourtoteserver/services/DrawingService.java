package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.entities.Drawing;
import com.fillyourtote.fillyourtoteserver.dao.DrawingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DrawingService {
    private final DrawingRepository drawingRepository;

    public DrawingService(DrawingRepository drawingRepository) {
        this.drawingRepository = drawingRepository;
    }

    @Transactional(readOnly = true)
    public List<Drawing> findAllDrawings() {
        return drawingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Drawing> findDrawingById(Long id) {
        return drawingRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Drawing> findDrawingBySlug(String slug) {
        return drawingRepository.findBySlug(slug);
    }
}
package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
    Optional<Drawing> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
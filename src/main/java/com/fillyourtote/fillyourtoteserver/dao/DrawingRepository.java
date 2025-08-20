package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}
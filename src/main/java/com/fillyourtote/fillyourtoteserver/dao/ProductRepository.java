package com.fillyourtote.fillyourtoteserver.dao;

import com.fillyourtote.fillyourtoteserver.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

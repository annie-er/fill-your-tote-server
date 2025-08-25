package com.fillyourtote.fillyourtoteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fillyourtote.fillyourtoteserver.dao.ProductRepository;
import com.fillyourtote.fillyourtoteserver.entities.Product;
import com.fillyourtote.fillyourtoteserver.services.ProductService;

@SpringBootApplication
public class FillYourToteServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FillYourToteServerApplication.class, args);
    }
}
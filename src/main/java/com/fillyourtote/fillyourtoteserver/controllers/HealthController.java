package com.fillyourtote.fillyourtoteserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        try {
            Connection connection = dataSource.getConnection();
            connection.close();

            response.put("status", "UP");
            response.put("database", "UP");
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("database", "DOWN");
            response.put("error", e.getMessage());
            response.put("timestamp", System.currentTimeMillis());

            return ResponseEntity.status(503).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Fill Your Tote API is running");
        response.put("status", "UP");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
}
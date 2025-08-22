package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.ContactMessage;
import com.fillyourtote.fillyourtoteserver.services.ContactMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactMessageRestController {

    private final ContactMessageService service;

    public ContactMessageRestController(ContactMessageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContactMessage> submitContactForm(@Valid @RequestBody ContactMessage contactMessage) {
        ContactMessage savedMessage = service.saveContactMessage(contactMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping
    public List<ContactMessage> getAllContactMessages() {
        return service.findAllContactMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getContactMessage(@PathVariable Long id) {
        return ResponseEntity.of(service.findContactMessageById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactMessage(@PathVariable Long id) {
        service.deleteContactMessage(id);
        return ResponseEntity.noContent().build();
    }
}
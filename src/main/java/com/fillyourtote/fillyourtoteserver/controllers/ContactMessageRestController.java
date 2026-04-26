package com.fillyourtote.fillyourtoteserver.controllers;

import com.fillyourtote.fillyourtoteserver.entities.ContactMessage;
import com.fillyourtote.fillyourtoteserver.services.AzureBlobService;
import com.fillyourtote.fillyourtoteserver.services.ContactMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactMessageRestController {

    private final ContactMessageService service;
    private final AzureBlobService azureBlobService;

    public ContactMessageRestController(ContactMessageService service,
                                        AzureBlobService azureBlobService) {
        this.service = service;
        this.azureBlobService = azureBlobService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContactMessage> submitContactForm(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            @RequestParam(value = "pronouns", required = false) String pronouns,
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "websiteOrProfile", required = false) String websiteOrProfile,
            @RequestParam(value = "dueDate", required = false) String dueDate,
            @RequestParam(value = "budget", required = false) String budget,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        ContactMessage contactMessage = new ContactMessage(
                fullName, pronouns, email, company,
                websiteOrProfile, dueDate, budget, message
        );

        if (file != null && !file.isEmpty()) {
            String url = azureBlobService.uploadFile(file);
            contactMessage.setAttachmentUrl(url);
        }

        ContactMessage saved = service.saveContactMessage(contactMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<ContactMessage> getAllContactMessages() {
        return service.findAllContactMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getContactMessage(@PathVariable Long id) {
        return ResponseEntity.of(service.findContactMessageById(id));
    }

    @GetMapping("/{id}/attachment")
    public ResponseEntity<Map<String, String>> getAttachmentUrl(@PathVariable Long id) {
        return service.findContactMessageById(id)
                .filter(msg -> msg.getAttachmentUrl() != null)
                .map(msg -> {
                    String downloadUrl = azureBlobService.generateDownloadUrl(
                            msg.getAttachmentUrl(), 15
                    );
                    Map<String, String> response = new HashMap<>();
                    response.put("url", downloadUrl);
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContactMessage(@PathVariable Long id) {
        service.deleteContactMessage(id);
        return ResponseEntity.noContent().build();
    }
}
package com.fillyourtote.fillyourtoteserver.services;

import com.fillyourtote.fillyourtoteserver.entities.ContactMessage;
import com.fillyourtote.fillyourtoteserver.dao.ContactMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @Transactional(readOnly = true)
    public List<ContactMessage> findAllContactMessages() {
        return contactMessageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ContactMessage> findContactMessageById(Long id) {
        return contactMessageRepository.findById(id);
    }

    public ContactMessage saveContactMessage(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

    public void deleteContactMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }
}
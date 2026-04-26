package com.fillyourtote.fillyourtoteserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import java.util.Objects;

@Entity
public class ContactMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(length = 100)
    private String pronouns;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(length = 255)
    private String company;

    @Column(length = 500)
    private String websiteOrProfile;

    @Column(length = 255)
    private String dueDate;

    @Column(length = 255)
    private String budget;

    @NotBlank(message = "Message is required")
    @Column(length = 2000)
    private String message;

    @Column(length = 500)
    private String attachmentUrl;

    public ContactMessage() {
    }

    public ContactMessage(String fullName, String pronouns, String email, String company,
                          String websiteOrProfile, String dueDate, String budget, String message) {
        this(null, fullName, pronouns, email, company, websiteOrProfile, dueDate, budget, message);
    }

    public ContactMessage(Long id, String fullName, String pronouns, String email, String company,
                          String websiteOrProfile, String dueDate, String budget, String message) {
        this.id = id;
        this.fullName = fullName;
        this.pronouns = pronouns;
        this.email = email;
        this.company = company;
        this.websiteOrProfile = websiteOrProfile;
        this.dueDate = dueDate;
        this.budget = budget;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsiteOrProfile() {
        return websiteOrProfile;
    }

    public void setWebsiteOrProfile(String websiteOrProfile) {
        this.websiteOrProfile = websiteOrProfile;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactMessage that = (ContactMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(pronouns, that.pronouns) &&
                Objects.equals(email, that.email) &&
                Objects.equals(company, that.company) &&
                Objects.equals(websiteOrProfile, that.websiteOrProfile) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(budget, that.budget) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, pronouns, email, company, websiteOrProfile, dueDate, budget, message);
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", pronouns='" + pronouns + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", websiteOrProfile='" + websiteOrProfile + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", budget='" + budget + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}